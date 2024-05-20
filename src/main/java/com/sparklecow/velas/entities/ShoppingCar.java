package com.sparklecow.velas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparklecow.velas.entities.candle.Candle;
import com.sparklecow.velas.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "shopping_car")
public class ShoppingCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private List<Candle> candles = new ArrayList<>();
    @JsonIgnore
    @OneToOne(mappedBy = "shoppingCar")
    private User user;
    private Double totalPrice = 0.0;

    public ShoppingCar(Long id, List<Candle> candles) {
        this.id = id;
        this.candles = candles;
    }

    public void addProduct(Candle candle){
        this.candles.add(candle);
        calculateTotalPrice();
    }

    public void removeProduct(Candle candle){
        this.candles.remove(candle);
        calculateTotalPrice();
    }

    public void calculateTotalPrice(){
        candles.forEach(candle -> this.totalPrice += candle.getPrice());
    }
}
