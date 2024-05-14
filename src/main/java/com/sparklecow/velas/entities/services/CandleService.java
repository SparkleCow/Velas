package com.sparklecow.velas.entities.services;

import com.sparklecow.velas.entities.candle.CandleResponseDto;
import com.sparklecow.velas.entities.candle.CandleRequestDto;
import com.sparklecow.velas.entities.candle.CandleUpdateDto;
import com.sparklecow.velas.entities.candle.Category;

import java.util.List;

public interface CandleService extends CrudService<CandleRequestDto, CandleResponseDto, CandleUpdateDto>{
    public List<CandleResponseDto> findByCategory(Category category);
}
