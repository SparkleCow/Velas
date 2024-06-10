package com.sparklecow.velas.services.ingredient;

import com.sparklecow.velas.entities.ingredient.IngredientRequestDto;
import com.sparklecow.velas.entities.ingredient.IngredientResponseDto;
import com.sparklecow.velas.entities.ingredient.IngredientUpdateDto;
import com.sparklecow.velas.services.ingredient.IngredientService;

import java.util.List;

public class IngredientServiceImp implements IngredientService {
    @Override
    public IngredientResponseDto create(IngredientRequestDto ingredientRequestDto) {
        return null;
    }

    @Override
    public List<IngredientResponseDto> findAll() {
        return null;
    }

    @Override
    public IngredientResponseDto findById(Long id) {
        return null;
    }

    @Override
    public IngredientResponseDto update(IngredientUpdateDto ingredientUpdateDto, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public IngredientResponseDto findByName(String name) {
        return null;
    }

    @Override
    public IngredientResponseDto findByPrice(Double price) {
        return null;
    }

    @Override
    public IngredientResponseDto findByCandle(Long id) {
        return null;
    }
}
