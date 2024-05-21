package com.sparklecow.velas.entities.candle;

import com.sparklecow.velas.entities.ingredient.Ingredient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@Builder
@Table(name = "candles")
public class Candle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String principalImage;
    private Integer stock;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;
    @OneToMany(mappedBy = "candle", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();
    private Double price = 0.0;

    public Candle(){}

    private void updateStock(){
        this.stock--;
    }
}
