package com.sparklecow.velas.services.candle;

import com.sparklecow.velas.entities.candle.*;
import com.sparklecow.velas.exceptions.NotFoundException;
import com.sparklecow.velas.services.CrudService;

import java.util.List;

public interface CandleService extends CrudService<CandleRequestDto, CandleResponseDto, CandleUpdateDto> {
    public List<CandleResponseDto> findByCategory(Category category);
    Candle findCandleById(Long id) throws NotFoundException;
}
