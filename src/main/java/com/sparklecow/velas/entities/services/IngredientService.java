package com.sparklecow.velas.entities.services;

import com.sparklecow.velas.entities.ingredient.IngredientRequestDto;
import com.sparklecow.velas.entities.ingredient.IngredientResponseDto;
import com.sparklecow.velas.entities.ingredient.IngredientUpdateDto;

public interface IngredientService extends CrudService<IngredientRequestDto, IngredientResponseDto, IngredientUpdateDto>{
    IngredientResponseDto findByName(String name);
    IngredientResponseDto findByPrice(Double price);
    IngredientResponseDto findByCandle(Double price, String name);
}
