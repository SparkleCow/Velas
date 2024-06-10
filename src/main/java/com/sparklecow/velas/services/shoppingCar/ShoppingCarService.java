package com.sparklecow.velas.services.shoppingCar;

import com.sparklecow.velas.entities.shoppingCar.ShoppingCar;
import com.sparklecow.velas.exceptions.NotFoundException;

public interface ShoppingCarService{
    void addProduct(Long id, ShoppingCar shoppingCar) throws NotFoundException;
    void addProducts(Long id, Long amount, ShoppingCar shoppingCar) throws NotFoundException;
    void removeProduct(Long candleId, ShoppingCar shoppingCar);
    void removeProducts(Long candleId, ShoppingCar shoppingCar) throws NotFoundException;
    void removeAllProducts(ShoppingCar shoppingCar);
    void buyProducts(ShoppingCar shoppingCar);
}
