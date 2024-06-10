package com.sparklecow.velas.entities.shoppingCar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparklecow.velas.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shopping_car")
public class ShoppingCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CarItem> candles = new ArrayList<>();
    @JsonIgnore
    @OneToOne(mappedBy = "shoppingCar")
    private User user;
    private Double totalPrice = 0.0;

    public ShoppingCar(Long id, List<CarItem> candles) {
        this.id = id;
        this.candles = candles;
    }

    public void calculateTotalPrice(){
        this.totalPrice = 0.0;
        candles.forEach(carItem -> this.totalPrice +=
                carItem.getCandle().getPrice()*carItem.getQuantity());
    }

    public void buyAll(){
        //TODO Implementar servicio de compra
        this.candles.clear();
        this.totalPrice = 0.0;
    }

    @Override
    public String toString() {
        return "ShoppingCar{" +
                "id=" + id +
                ", candles=" + candles +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
