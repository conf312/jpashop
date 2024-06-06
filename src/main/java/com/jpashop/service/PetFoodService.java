package com.jpashop.service;

import com.jpashop.domain.PetFood;
import com.jpashop.repository.PetFoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PetFoodService {
    private final PetFoodRepository petFoodRepository;

    public PetFood save(PetFood petFood) {
        return petFoodRepository.save(petFood);
    }
}
