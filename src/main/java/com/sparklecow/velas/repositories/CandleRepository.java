package com.sparklecow.velas.repositories;

import com.sparklecow.velas.entities.candle.Candle;
import com.sparklecow.velas.entities.candle.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandleRepository extends JpaRepository<Candle, Long> {
    List<Candle> findByCategory(Category category);
}
