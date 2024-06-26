package com.sparklecow.velas.services.mappers;

import com.sparklecow.velas.entities.candle.Candle;
import com.sparklecow.velas.entities.candle.CandleRequestDto;
import com.sparklecow.velas.entities.candle.CandleResponseDto;
import com.sparklecow.velas.entities.candle.CandleUpdateDto;
import com.sparklecow.velas.entities.ingredient.Ingredient;
import com.sparklecow.velas.entities.ingredient.IngredientRequestDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if (candleRequestDto.ingredients() != null && !candleRequestDto.ingredients().isEmpty()) {
            List<Ingredient> ingredients = candleRequestDto.ingredients().stream().map(x -> new Ingredient(x.name(), x.amount())).toList();
            ingredients.forEach(x -> x.setCandle(candle));
            candle.setIngredients(ingredients);
            candle.setPrice(calculatePrice(ingredients));
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
        }if (candleUpdateDto.ingredients() != null) {
            List<Ingredient> updatedIngredients = new ArrayList<>();
            for (IngredientRequestDto dto : candleUpdateDto.ingredients()) {
                Optional<Ingredient> existingIngredientOptional = candle.getIngredients().stream()
                        .filter(existingIngredient -> existingIngredient.getName().equals(dto.name()))
                        .findFirst();
                if (existingIngredientOptional.isPresent()) {
                    Ingredient existingIngredient = existingIngredientOptional.get();
                    existingIngredient.setAmount(dto.amount());
                    existingIngredient.setPrice(existingIngredient.calculatePrice());
                    updatedIngredients.add(existingIngredient);
                } else {
                    Ingredient newIngredient = new Ingredient(dto.name(), dto.amount());
                    newIngredient.setCandle(candle);
                    updatedIngredients.add(newIngredient);
                }
            }
            candle.setIngredients(updatedIngredients);
            candle.setPrice(calculatePrice(updatedIngredients));
        }
        return candle;
    }

    private Double calculatePrice(List<Ingredient> ingredients){
        return ingredients.stream().mapToDouble(Ingredient::getPrice).sum();
    }

}
