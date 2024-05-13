package com.sparklecow.velas.entities.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IngredientRequestDto(
        @NotNull @NotBlank
        Ingredients name,
        @NotNull @NotBlank
        Double amount) {
}
