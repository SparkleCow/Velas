package com.sparklecow.velas.services.ingredient;

import com.sparklecow.velas.entities.ingredient.IngredientRequestDto;
import com.sparklecow.velas.entities.ingredient.IngredientResponseDto;
import com.sparklecow.velas.entities.ingredient.IngredientUpdateDto;
import com.sparklecow.velas.services.CrudService;

public interface IngredientService extends CrudService<IngredientRequestDto, IngredientResponseDto, IngredientUpdateDto> {
    IngredientResponseDto findByName(String name);
    IngredientResponseDto findByPrice(Double price);
    IngredientResponseDto findByCandle(Long id);
}
