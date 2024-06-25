package com.sparklecow.velas.controllers;

import com.sparklecow.velas.entities.candle.CandleResponseDto;
import com.sparklecow.velas.entities.candle.CandleUpdateDto;
import com.sparklecow.velas.entities.candle.Category;
import com.sparklecow.velas.entities.candle.CandleRequestDto;
import com.sparklecow.velas.exceptions.NotFoundException;
import com.sparklecow.velas.services.candle.CandleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/candle")
@CrossOrigin(origins = "*")
@Tag(name = "CandleController", description = "Candle API")
public class CandleController {

    private final CandleService candleService;

    //Required an ADMIN role
    @PostMapping()
    public ResponseEntity<CandleResponseDto> createCandle(@RequestBody @Valid CandleRequestDto candleRequestDto){
        CandleResponseDto candleResponseDto = candleService.create(candleRequestDto);
        URI uri = URI.create("/api/v1/candle/"+candleResponseDto.id());
        return ResponseEntity.created(uri).body(candleResponseDto);
    }

    @GetMapping()
    public ResponseEntity<List<CandleResponseDto>> findAllCandles(){
        return ResponseEntity.ok(candleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandleResponseDto> findCandleById(@PathVariable Long id) throws NotFoundException{
        return ResponseEntity.ok(candleService.findById(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<CandleResponseDto>> findCandleByCategory(@PathVariable String category){
        return ResponseEntity.ok(candleService.findByCategory(Category.valueOf(category)));
    }

    //Required an ADMIN role
    @PutMapping("/{id}")
    public ResponseEntity<CandleResponseDto> updateCandle(@PathVariable Long id,
                                                    @RequestBody CandleUpdateDto candleUpdateDto) throws NotFoundException {
        return ResponseEntity.ok(candleService.update(candleUpdateDto,id));
    }

    //Required an ADMIN role
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCandle(@PathVariable Long id) throws NotFoundException {
        candleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
