package com.sparklecow.velas.entities.services;

import com.sparklecow.velas.entities.candle.Candle;
import com.sparklecow.velas.entities.candle.CandleRequestDto;
import com.sparklecow.velas.entities.candle.CandleResponseDto;
import com.sparklecow.velas.entities.candle.Category;
import com.sparklecow.velas.repositories.CandleRepository;
import com.sparklecow.velas.services.CandleServiceImp;
import com.sparklecow.velas.services.utils.CandleMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CandleServiceImpTest {

    @InjectMocks
    private CandleServiceImp candleService;

    @Mock
    private CandleMapper candleMapper;

    @Mock
    private CandleRepository candleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        //Given
        CandleRequestDto candleRequestDto = new CandleRequestDto("Vela de naranja",
                "Vela hecha con el amor de mamá",
                "xxxxxxxxxxx",
                20,
                Category.valueOf("AROMATIC"),
                List.of("xxxxxxxxxxx"),
                List.of());

        Candle candle = Candle.builder()
                .name("Vela de naranja")
                .description("Vela hecha con el amor de mamá")
                .principalImage("xxxxxxxxxxx")
                .images(List.of("xxxxxxxxxxx"))
                .stock(20)
                .category(Category.valueOf("AROMATIC"))
                .build();

        CandleResponseDto candleResponseDto = new CandleResponseDto(1L,
                "Vela de naranja",
                "Vela hecha con el amor de mamá",
                "xxxxxxxxxxx",
                20,
                Category.valueOf("AROMATIC"),
                List.of("xxxxxxxxxxx"),
                0.0);

        //Mocks
        Mockito.when(candleMapper.toCandle(candleRequestDto)).thenReturn(candle);
        Mockito.when(candleRepository.save(candle)).thenReturn(candle);
        Mockito.when(candleMapper.toCandleDto(candle)).thenReturn(candleResponseDto);

        //When
        CandleResponseDto candleResult = candleService.create(candleRequestDto);

        //Then
        assertNotNull(candleResult);
        assertEquals(candleResult.name(), candleRequestDto.name());
    }
}