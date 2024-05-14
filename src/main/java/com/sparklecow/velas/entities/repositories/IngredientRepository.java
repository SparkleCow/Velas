package com.sparklecow.velas.entities.repositories;

import com.sparklecow.velas.entities.ingredient.Ingredient;
import com.sparklecow.velas.entities.ingredient.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Ingredient findByName(Ingredients name);
    Ingredient findByPrice(Double price);
    List<Ingredient> findByCandleId(Long candleId);
}
