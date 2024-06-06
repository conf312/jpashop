package com.jpashop.repository;

import com.jpashop.domain.PetFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetFoodRepository extends JpaRepository<PetFood, Long> {
}
