package com.jpashop.web;

import com.jpashop.domain.Brand;
import com.jpashop.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BrandController {
    private final BrandService brandService;

    @PostMapping("/brand")
    public void save(@RequestBody Brand brand) {
        brandService.save(brand);
    }

    @GetMapping("/brand")
    public List<Brand> findAll() {
        return brandService.findAll();
    }

    @GetMapping("/brand/petfood/{name}")
    public Brand findBrandsByName(@PathVariable(name = "name") String name) {
        return brandService.findBrandsByName(name);
    }

    @GetMapping("/brand/{petFoodName}")
    public Brand findBrandsByPetFoodName(@PathVariable(name = "petFoodName") String petFoodName) {
        return brandService.findBrandsByPetFoodName(petFoodName);
    }

    @GetMapping("/test")
    public void testJoin() {
        System.out.println("==> 테스트");
        brandService.testJoin();
    }
}
