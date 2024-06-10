package com.sparklecow.velas.services.candle;

import com.sparklecow.velas.entities.candle.*;
import com.sparklecow.velas.exceptions.NotFoundException;
import com.sparklecow.velas.repositories.CandleRepository;
import com.sparklecow.velas.services.mappers.CandleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandleServiceImp implements CandleService {

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

    @Override
    public List<CandleResponseDto> findAll() {
        return candleRepository.findAll().stream().map(candleMapper::toCandleDto).toList();
    }

    @Override
    public CandleResponseDto findById(Long id) throws NotFoundException{
        Candle candle = candleRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found", Candle.class));
        return candleMapper.toCandleDto(candle);
    }

    @Override
    public Candle findCandleById(Long id) throws NotFoundException {
        return candleRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found", Candle.class));
    }

    @Override
    public CandleResponseDto update(CandleUpdateDto candleUpdateDto, Long id) throws NotFoundException {
        Candle candle = candleRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found", Candle.class));
        candle = candleMapper.updateCandle(candleUpdateDto, candle);
        return candleMapper.toCandleDto(candleRepository.save(candle));
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        candleRepository.findById(id).orElseThrow(() ->new NotFoundException("Not found", Candle.class));
        candleRepository.deleteById(id);
    }
}
