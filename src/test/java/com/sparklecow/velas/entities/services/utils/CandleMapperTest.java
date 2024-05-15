package com.sparklecow.velas.entities.services.utils;

import com.sparklecow.velas.entities.candle.*;
import com.sparklecow.velas.entities.ingredient.Ingredient;
import com.sparklecow.velas.entities.ingredient.IngredientRequestDto;
import com.sparklecow.velas.entities.ingredient.Ingredients;
import com.sparklecow.velas.services.utils.CandleMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CandleMapperTest {

    private static CandleMapper candleMapper;

    @BeforeAll
    static void beforeAll() {
        candleMapper = new CandleMapper();
    }

    @Test
    void toCandle() {
        //given
        CandleRequestDto candleRequestDto = new CandleRequestDto("Velon de naranja",
                "Velon de naranja hecho con los mejores materiales",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRabxp0OYTyIX0Pr_D19eDaJBPFWnGWu5hU8QlBbqunw_PpTyEMNhtFOknBIdfypvy9RY0&usqp=CAU",
                20,
                Category.OIL_LAMP,
                List.of("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRabxp0OYTyIX0Pr_D19eDaJBPFWnGWu5hU8QlBbqunw_PpTyEMNhtFOknBIdfypvy9RY0&usqp=CAU"),
                List.of());

        //when
        Candle candle = candleMapper.toCandle(candleRequestDto);

        //then
        assertNotNull(candle);
        assertEquals(candleRequestDto.name(), candle.getName());
        assertEquals(candleRequestDto.description(), candle.getDescription());
        assertEquals(candleRequestDto.images(), candle.getImages());
        assertEquals(candle.getPrice(), 0.0);
    }

    @Test
    void toCandleDto() {
        //given
        Candle candle= Candle.builder()
                .id(1L)
                .name("Velon de naranja")
                .description("Velon de naranja hecho con los mejores materiales")
                .principalImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRabxp0OYTyIX0Pr_D19eDaJBPFWnGWu5hU8QlBbqunw_PpTyEMNhtFOknBIdfypvy9RY0&usqp=CAU")
                .stock(20)
                .category(Category.OIL_LAMP)
                .images(List.of("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRabxp0OYTyIX0Pr_D19eDaJBPFWnGWu5hU8QlBbqunw_PpTyEMNhtFOknBIdfypvy9RY0&usqp=CAU"))
                .ingredients(List.of(new Ingredient(Ingredients.BEESWAX, 1.0), new Ingredient(Ingredients.WICK, 2.0)))
                .build();

        //when
        CandleResponseDto candleResponseDto = candleMapper.toCandleDto(candle);

        //then
        assertNotNull(candleResponseDto);
        assertEquals(candle.getName(), candleResponseDto.name());
        assertNotNull(candleResponseDto.id());
        assertEquals(candle.getDescription(), candleResponseDto.description());
        assertEquals(candle.getPrincipalImage(), candleResponseDto.principalImage());
        assertEquals(candle.getStock(), candleResponseDto.stock());
        assertEquals(candle.getCategory(), candleResponseDto.category());
        assertNotEquals(candleResponseDto.price(), 0.0);
    }

    @Test
    void updateCandle() {
        //given
        Candle candle= Candle.builder()
                .id(1L)
                .name("Velon de naranja")
                .description("Velon de naranja hecho con los mejores materiales")
                .principalImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRabxp0OYTyIX0Pr_D19eDaJBPFWnGWu5hU8QlBbqunw_PpTyEMNhtFOknBIdfypvy9RY0&usqp=CAU")
                .stock(20)
                .category(Category.OIL_LAMP)
                .images(List.of("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRabxp0OYTyIX0Pr_D19eDaJBPFWnGWu5hU8QlBbqunw_PpTyEMNhtFOknBIdfypvy9RY0&usqp=CAU"))
                .build();

        CandleUpdateDto candleUpdate = new CandleUpdateDto("",
                "",
                "",
                null,
                null,
                null,
                List.of(new IngredientRequestDto(Ingredients.BEESWAX, 1.0), new IngredientRequestDto(Ingredients.WICK, 2.0)));

        //when
        Candle candleAfterUpdate = candleMapper.updateCandle(candleUpdate,candle);

        //then
        assertNotNull(candleAfterUpdate);
        assertNotEquals(candle.getName(), candleUpdate.name());
        assertNotEquals(candle.getDescription(), candleUpdate.description());
        assertNotEquals(candle.getPrincipalImage(), candleUpdate.principalImage());
        assertEquals(candle.getIngredients(), candleUpdate.ingredients());
        assertNotEquals(candle.getPrice(), 0.0);
    }
}