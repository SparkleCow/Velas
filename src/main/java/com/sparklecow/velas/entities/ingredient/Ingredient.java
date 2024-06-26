package com.sparklecow.velas.entities.ingredient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparklecow.velas.entities.candle.Candle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "candle_id")
    private Candle candle;

    public Ingredient() {
    }

    public Ingredient(Ingredients name, Double amount){
        this.name = name;
        this.amount = amount;
        this.pricePerUnit = name.pricePerUnit;
        this.price = calculatePrice();
    }

    public Double calculatePrice(){
        return amount * pricePerUnit;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name=" + name +
                ", amount=" + amount +
                ", pricePerUnit=" + pricePerUnit +
                ", price=" + price +
                '}';
    }
}
