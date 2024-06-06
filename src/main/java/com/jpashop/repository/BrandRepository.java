package com.jpashop.repository;

import com.jpashop.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("select b from Brand b join fetch b.petFoods pf where b.name = :name")
    Brand findBrandsByName(@Param("name") final String name);

    @Query("select b from Brand b left join fetch b.petFoods pf where pf.name = :petFoodName")
    Brand findBrandsByPetFoodName(@Param("petFoodName") final String petFoodName);
}
