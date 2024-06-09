package com.sparklecow.velas.repositories;

import com.sparklecow.velas.entities.shoppingCar.CarItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarItemRepository extends JpaRepository<CarItem, Long>{
}
