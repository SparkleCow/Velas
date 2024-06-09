package com.sparklecow.velas.entities.shoppingCar;

import com.sparklecow.velas.entities.candle.Candle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "car_item")
public class CarItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "candle_id")
    private Candle candle;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "shopping_car_id")
    private ShoppingCar shoppingCar;

    public CarItem(Candle candle, int quantity) {
        this.candle = candle;
        this.quantity = quantity;
    }
}
