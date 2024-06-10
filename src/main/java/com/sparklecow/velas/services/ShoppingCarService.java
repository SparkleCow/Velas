package com.sparklecow.velas.services;

import com.sparklecow.velas.entities.shoppingCar.ShoppingCar;

public interface ShoppingCarService{
    void addProduct(Long id, ShoppingCar shoppingCar);
    void addProducts(Long id, Long amount, ShoppingCar shoppingCar);
    void removeProduct(Long candleId, ShoppingCar shoppingCar);
    void removeProducts(Long candleId, ShoppingCar shoppingCar);
    void removeAllProducts(ShoppingCar shoppingCar);
}
