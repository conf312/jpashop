package com.jpashop.web;

import com.jpashop.domain.PetFood;
import com.jpashop.service.PetFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PetFoodController {
    private final PetFoodService petFoodService;

    @PostMapping("/petFood")
    public void save(@RequestBody PetFood petFood) {
        petFoodService.save(petFood);
    }
}
