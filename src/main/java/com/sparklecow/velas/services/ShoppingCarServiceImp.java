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

        for (CarItem item : items) {
            if (item.getCandle().getId().equals(candle.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                candle.removeStock(1);
                candleRepository.save(candle);
                shoppingCar.calculateTotalPrice();
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

        for (CarItem item : items) {
            if (item.getCandle().getId().equals(candle.getId())) {
                item.setQuantity(item.getQuantity() + amount.intValue());
                candle.removeStock(amount.intValue());
                candleRepository.save(candle);
                shoppingCar.calculateTotalPrice();
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
        CarItem carItem = shoppingCar.getCandles().stream()
                .filter(x -> x.getCandle().getId().equals(candleId)).findFirst()
                .orElseThrow(()->new RuntimeException("Candle not found"));

        int quantity = carItem.getQuantity();
        Candle candle = carItem.getCandle();
        candle.addStock(quantity);
        candleRepository.save(candle);

        //Filtra los elementos que no tienen el candleId especificado y los recolecta en una lista
        List<CarItem> carItems = shoppingCar.getCandles().stream()
                .filter(x -> !x.getCandle().getId().equals(candleId)).toList();
        shoppingCar.setCandles(carItems);
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
                    }}).toList();

        shoppingCar.setCandles(carItems);
        shoppingCar.calculateTotalPrice();
        shoppingCarRepository.save(shoppingCar);
    }

    @Override
    public void removeAllProducts() {
    }
}
