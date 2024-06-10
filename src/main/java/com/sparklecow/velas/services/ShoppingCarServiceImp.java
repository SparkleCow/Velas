package com.sparklecow.velas.services;

import com.sparklecow.velas.entities.shoppingCar.CarItem;
import com.sparklecow.velas.entities.shoppingCar.ShoppingCar;
import com.sparklecow.velas.entities.candle.Candle;
import com.sparklecow.velas.repositories.CandleRepository;
import com.sparklecow.velas.repositories.CarItemRepository;
import com.sparklecow.velas.repositories.ShoppingCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShoppingCarServiceImp implements ShoppingCarService{

    private final CandleRepository candleRepository;
    private final ShoppingCarRepository shoppingCarRepository;
    private final CarItemRepository carItemRepository;
    @Override
    public void addProduct(Long id, ShoppingCar shoppingCar) {
        Candle candle = candleRepository.findById(id).orElseThrow(()-> new RuntimeException("Candle not found"));

        if(candle.getStock()<1){
            throw new RuntimeException("Candle without stock");
        }

        List<CarItem> items = shoppingCar.getCandles();

        for(int i=0; i<items.size();i++){
            if (items.get(i).getCandle().getId().equals(candle.getId())) {
                items.get(i).setQuantity(items.get(i).getQuantity() + 1);
                candle.removeStock(1);
                candleRepository.save(candle);
                shoppingCar.setCandles(items);
                shoppingCar.calculateTotalPrice();
                shoppingCarRepository.save(shoppingCar);
                return;
            }
        }
        items.add(new CarItem(candle, 1));
        candle.removeStock(1);
        candleRepository.save(candle);
        shoppingCar.setCandles(items);
        shoppingCar.calculateTotalPrice();
        shoppingCarRepository.save(shoppingCar);
    }

    @Override
    public void addProducts(Long id, Long amount, ShoppingCar shoppingCar) {
        Candle candle = candleRepository.findById(id).orElseThrow(()-> new RuntimeException("Candle not found"));

        if(candle.getStock()<amount){
            throw new RuntimeException("Candle without stock");
        }

        List<CarItem> items = shoppingCar.getCandles();

        for(int i=0; i<items.size();i++){
            if (items.get(i).getCandle().getId().equals(candle.getId())) {
                items.get(i).setQuantity(items.get(i).getQuantity() + amount.intValue());
                candle.removeStock(amount.intValue());
                candleRepository.save(candle);
                shoppingCar.setCandles(items);
                shoppingCar.calculateTotalPrice();
                shoppingCarRepository.save(shoppingCar);
                return;
            }
        }

        items.add(new CarItem(candle, amount.intValue()));
        candle.removeStock(amount.intValue());
        candleRepository.save(candle);
        shoppingCar.setCandles(items);
        shoppingCar.calculateTotalPrice();
        shoppingCarRepository.save(shoppingCar);
    }

    @Override
    public void removeProducts(Long candleId, ShoppingCar shoppingCar) {
        //Encuentra el CarItem correspondiente al candleId especificado
        CarItem carItemToRemove = shoppingCar.getCandles().stream()
                .filter(x -> x.getCandle().getId().equals(candleId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Candle not found"));

        //Incrementa el stock del candle correspondiente al CarItem que vamos a eliminar
        Candle candleToRemove = carItemToRemove.getCandle();
        int quantityToRemove = carItemToRemove.getQuantity();
        candleToRemove.addStock(quantityToRemove);
        candleRepository.save(candleToRemove);

        //Filtra los elementos que no tienen el candleId especificado y los recolecta en una nueva lista mutable
        List<CarItem> remainingCarItems = shoppingCar.getCandles().stream()
                .filter(x -> !x.getCandle().getId().equals(candleId))
                .collect(Collectors.toList());

        shoppingCar.setCandles(remainingCarItems);
        shoppingCar.calculateTotalPrice();
        shoppingCarRepository.save(shoppingCar);
    }

    @Override
    public void removeProduct(Long candleId, ShoppingCar shoppingCar) {
        // Modifica los elementos del carrito
        List<CarItem> carItems = shoppingCar.getCandles().stream()
                .peek(x -> {
                    if(x.getCandle().getId().equals(candleId) && x.getQuantity()>1){
                        x.getCandle().addStock(1);
                        candleRepository.save(x.getCandle());
                        x.setQuantity(x.getQuantity() - 1);
                    }}).collect(Collectors.toList());

        shoppingCar.setCandles(carItems);
        shoppingCar.calculateTotalPrice();
        shoppingCarRepository.save(shoppingCar);
    }

    @Override
    public void removeAllProducts(ShoppingCar shoppingCar) {
        shoppingCar.getCandles().forEach(x -> {
            x.getCandle().addStock(x.getQuantity());
            candleRepository.save(x.getCandle());
        });
        shoppingCar.getCandles().clear();
        shoppingCar.setTotalPrice(0.0);
        shoppingCarRepository.save(shoppingCar);
    }
}
