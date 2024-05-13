package com.sparklecow.velas.entities.candle;

import com.sparklecow.velas.entities.ingredient.Ingredient;

import java.util.List;

public record CandleUpdateDto(String name,
                              String description,
                              String principalImage,
                              Integer stock,
                              Category category,
                              List<String> images,
                              List<Ingredient> ingredients){
}
