package com.sparklecow.velas.entities.Candle;

import com.sparklecow.velas.entities.Ingredient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CandleRequestDto(
        @NotNull @NotBlank
        String name,
        @NotNull @NotBlank
        String description,
        @NotNull @NotBlank
        String image,
        @NotNull @NotBlank
        Integer stock,
        @NotNull @NotBlank
        Category category,
        @NotNull
        List<String> images,
        List<Ingredient> ingredients
) {
}
