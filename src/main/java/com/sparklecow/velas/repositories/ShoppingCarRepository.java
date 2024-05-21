package com.sparklecow.velas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCarRepository extends JpaRepository<ShoppingCarRepository, Long> {
}
