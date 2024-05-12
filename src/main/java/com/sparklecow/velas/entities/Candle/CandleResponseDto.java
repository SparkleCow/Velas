package com.sparklecow.velas.entities.Candle;

import java.util.List;

public record CandleResponseDto(Long id,
                                String name,
                                String description,
                                String principalImage,
                                Integer stock,
                                Category category,
                                List<String> images,
                                Double price) {
}

