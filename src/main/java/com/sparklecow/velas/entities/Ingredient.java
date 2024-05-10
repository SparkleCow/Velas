package com.sparklecow.velas.entities;

import com.sparklecow.velas.entities.Candle.Candle;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Ingredients name;
    private Double amount;
    private Double pricePerUnit;
    private Double price;
    @ManyToOne
    @JoinColumn(name = "candle_id")
    private Candle candle;
    public Ingredient(Ingredients name, Double amount){
        this.name = name;
        this.amount = amount;
        this.pricePerUnit = name.pricePerUnit;
        this.price = calculatePrice();
    }

    private Double calculatePrice(){
        return amount * pricePerUnit;
    }
}
