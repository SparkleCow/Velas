package com.sparklecow.velas.entities.services.utils;

import com.sparklecow.velas.entities.candle.Candle;
import com.sparklecow.velas.entities.candle.CandleRequestDto;
import com.sparklecow.velas.entities.candle.CandleResponseDto;
import com.sparklecow.velas.entities.candle.CandleUpdateDto;
import com.sparklecow.velas.entities.ingredient.Ingredient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandleMapper {
    public Candle toCandle(CandleRequestDto candleRequestDto){
        Candle candle = Candle.builder()
                .name(candleRequestDto.name())
                .description(candleRequestDto.description())
                .principalImage(candleRequestDto.principalImage())
                .images(candleRequestDto.images())
                .stock(candleRequestDto.stock())
                .category(candleRequestDto.category())
                .build();
        if(candleRequestDto.ingredients()!=null){
            candle.setIngredients(candleRequestDto.ingredients());
            candle.setPrice(calculatePrice(candleRequestDto.ingredients()));
        }
        return candle;
    }

    public CandleResponseDto toCandleDto(Candle candle){
        return new CandleResponseDto(candle.getId(), candle.getName(), candle.getDescription(), candle.getPrincipalImage(),
                candle.getStock(), candle.getCategory(), candle.getImages(), candle.getPrice());
    }

    public Candle updateCandle(CandleUpdateDto candleUpdateDto, Candle candle){
        if(candleUpdateDto.name()!=null && !candleUpdateDto.name().equals("")){
            candle.setName(candleUpdateDto.name());
        }if(candleUpdateDto.description()!=null && !candleUpdateDto.description().equals("")){
            candle.setDescription(candleUpdateDto.description());
        }if(candleUpdateDto.principalImage()!=null && !candleUpdateDto.principalImage().equals("")){
            candle.setPrincipalImage(candleUpdateDto.principalImage());
        }if(candleUpdateDto.stock()!=null){
            candle.setStock(candleUpdateDto.stock());
        }if(candleUpdateDto.images()!=null){
            candle.setImages(candleUpdateDto.images());
        }if(candleUpdateDto.category()!=null){
            candle.setCategory(candleUpdateDto.category());
        }if(candleUpdateDto.ingredients()!=null){
            candle.setIngredients(candleUpdateDto.ingredients());
            candle.setPrice(calculatePrice(candleUpdateDto.ingredients()));
        }
        return candle;
    }

    private Double calculatePrice(List<Ingredient> ingredients){
        return ingredients.stream().mapToDouble(Ingredient::getPrice).sum();
    }

}
