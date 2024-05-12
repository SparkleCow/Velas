package com.sparklecow.velas.entities.Candle;

import com.sparklecow.velas.entities.Ingredient;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
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
    private String principalImage;
    private Integer stock;
    private Category category;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;
    @OneToMany(mappedBy = "candle", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();
    private Double price = 0.0;

    public void toCandle(CandleRequestDto candleRequestDto){
        this.name = candleRequestDto.name();
        this.description = candleRequestDto.description();
        this.principalImage = candleRequestDto.image();
        this.images = candleRequestDto.images();
        this.stock = candleRequestDto.stock();
        this.category = candleRequestDto.category();
        if(candleRequestDto.ingredients()!=null){
            this.ingredients = candleRequestDto.ingredients();
            this.price = calculatePrice(candleRequestDto.ingredients());
        }
    }

    public CandleResponseDto ToCandleDto(){
        return new CandleResponseDto(this.id, this.name, this.description,
                this.principalImage, this.stock, this.category, this.images, this.price);
    }

    public void updateCandle(CandleUpdateDto candleUpdateDto){
        if(candleUpdateDto.name()!=null && !candleUpdateDto.name().equals("")){
            this.name = candleUpdateDto.name();
        }if(candleUpdateDto.description()!=null && !candleUpdateDto.description().equals("")){
            this.description = candleUpdateDto.description();
        }if(candleUpdateDto.principalImage()!=null && !candleUpdateDto.principalImage().equals("")){
            this.principalImage = candleUpdateDto.principalImage();
        }if(candleUpdateDto.stock()!=null){
            this.stock = candleUpdateDto.stock();
        }if(candleUpdateDto.images()!=null){
            this.images = candleUpdateDto.images();
        }if(candleUpdateDto.ingredients()!=null){
            this.ingredients = candleUpdateDto.ingredients();
            this.price = calculatePrice(candleUpdateDto.ingredients());
        }if(candleUpdateDto.category()!=null){
            this.category = candleUpdateDto.category();
        }
    }

    private Double calculatePrice(List<Ingredient> ingredients){
        return ingredients.stream().mapToDouble(Ingredient::getPrice).sum();
    }

    private void updateStock(){
        this.stock--;
    }
}
