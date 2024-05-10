package com.sparklecow.velas.entities.Candle;

import com.sparklecow.velas.entities.Ingredient;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "candles")
public class Candle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String image;
    private Integer stock;
    private Category category;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;
    @OneToMany(mappedBy = "candle", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;
    private Double price = 0.0;

    public void toCandle(CandleRequestDto candleRequestDto){
        this.name = candleRequestDto.;
        this.description = candleRequestDto.description;
        this.image = candleRequestDto.image;
        this.stock = candleRequestDto.stock;
        this.category = candleRequestDto.category;
        this.images = candleRequestDto.images;
        this.ingredients = candleRequestDto.ingredients;
    }

    private void calculatePrice(){
        ingredients.forEach(ingredient -> this.price += ingredient.getPrice());
    }
}
