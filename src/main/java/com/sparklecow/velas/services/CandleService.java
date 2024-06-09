package com.sparklecow.velas.services;

import com.sparklecow.velas.entities.candle.*;

import java.util.List;

public interface CandleService extends CrudService<CandleRequestDto, CandleResponseDto, CandleUpdateDto>{
    public List<CandleResponseDto> findByCategory(Category category);
    Candle findCandleById(Long id);
    void updateStock(Candle candle);
}
