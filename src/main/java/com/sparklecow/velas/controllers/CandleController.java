package com.sparklecow.velas.controllers;

import com.sparklecow.velas.entities.candle.CandleResponseDto;
import com.sparklecow.velas.entities.candle.CandleUpdateDto;
import com.sparklecow.velas.entities.candle.Category;
import com.sparklecow.velas.entities.candle.CandleRequestDto;
import com.sparklecow.velas.services.CandleService;
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

    @PostMapping()
    public ResponseEntity<CandleResponseDto> createCandle(@RequestBody CandleRequestDto candleRequestDto){
        CandleResponseDto candleResponseDto = candleService.create(candleRequestDto);
        URI uri = URI.create("/api/v1/candle/"+candleResponseDto.id());
        return ResponseEntity.created(uri).body(candleResponseDto);
    }

    @GetMapping()
    public ResponseEntity<List<CandleResponseDto>> findAll(){
        return ResponseEntity.ok(candleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandleResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(candleService.findById(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<CandleResponseDto>> findByCategory(@PathVariable String category){
        return ResponseEntity.ok(candleService.findByCategory(Category.valueOf(category)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandleResponseDto> update(@PathVariable Long id, @RequestBody CandleUpdateDto candleUpdateDto){
        return ResponseEntity.ok(candleService.update(candleUpdateDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        candleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
