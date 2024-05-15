package com.sparklecow.velas.services;

import com.sparklecow.velas.entities.candle.*;
import com.sparklecow.velas.repositories.CandleRepository;
import com.sparklecow.velas.services.mappers.CandleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
//TODO Implementar manejo de excepciones
public class CandleServiceImp implements CandleService{

    private final CandleRepository candleRepository;
    private final CandleMapper candleMapper;

    @Override
    public List<CandleResponseDto> findByCategory(Category category) {
        return candleRepository.findByCategory(category).stream().map(candleMapper::toCandleDto).toList();
    }

    @Override
    public CandleResponseDto create(CandleRequestDto candleRequestDto) {
        Candle candle = candleMapper.toCandle(candleRequestDto);
        Candle candleAfter = candleRepository.save(candle);
        return candleMapper.toCandleDto(candleAfter);
    }

    public Candle create2(Candle candle){
        return candleRepository.save(candle);
    }

    @Override
    public List<CandleResponseDto> findAll() {
        return candleRepository.findAll().stream().map(candleMapper::toCandleDto).toList();
    }

    @Override
    public CandleResponseDto findById(Long id) {
        Candle candle = candleRepository.findById(id).orElseThrow(() -> new RuntimeException("Candle not found"));
        return candleMapper.toCandleDto(candle);
    }

    @Override
    public CandleResponseDto update(CandleUpdateDto candleUpdateDto, Long id) {
        Candle candle = candleRepository.findById(id).orElseThrow(() -> new RuntimeException("Candle not found"));
        candle = candleMapper.updateCandle(candleUpdateDto, candle);
        return candleMapper.toCandleDto(candleRepository.save(candle));
    }

    @Override
    public void deleteById(Long id) {
        candleRepository.findById(id).orElseThrow(() -> new RuntimeException("Candle not found"));
        candleRepository.deleteById(id);
    }
}
