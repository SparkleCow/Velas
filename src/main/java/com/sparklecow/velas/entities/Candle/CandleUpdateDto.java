package com.sparklecow.velas.entities.Candle;

import com.sparklecow.velas.entities.Ingredient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

public record CandleUpdateDto(String name,
                              String description,
                              String principalImage,
                              Integer stock,
                              Category category,
                              List<String> images,
                              List<Ingredient> ingredients){
}
