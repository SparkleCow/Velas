package com.sparklecow.velas.repositories;

import com.sparklecow.velas.entities.shoppingCar.ShoppingCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCarRepository extends JpaRepository<ShoppingCar, Long> {
}
