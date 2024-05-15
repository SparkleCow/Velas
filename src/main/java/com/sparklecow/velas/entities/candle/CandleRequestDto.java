package com.sparklecow.velas.entities.candle;

import com.sparklecow.velas.entities.ingredient.IngredientRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CandleRequestDto(
        @NotNull @NotBlank
        String name,
        @NotNull @NotBlank
        String description,
        @NotNull @NotBlank
        String principalImage,
        @NotNull @NotBlank
        Integer stock,
        @NotNull @NotBlank
        Category category,
        @NotNull
        List<String> images,
        List<IngredientRequestDto> ingredients
) {
}
