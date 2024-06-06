package com.jpashop.service;

import com.jpashop.domain.Brand;
import com.jpashop.repository.BrandRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    public List<Brand> findAll() {
        List<Brand> list = brandRepository.findAll();
        System.out.println("==> findAll");
        System.out.println(list);
        return list;
    }

    public Brand findBrandsByName(String name) {
        Brand brand = brandRepository.findBrandsByName(name);
        System.out.println("==> findBrandsByName");
        System.out.println(brand);
        return brand;
    }

    public Brand findBrandsByPetFoodName(String petFoodName) {
//        Brand findBrandsByName = brandRepository.findBrandsByName("베베");
//        System.out.println("==> findBrandsByName");
//        System.out.println(findBrandsByName);
        Brand findBrandsByPetFoodName = brandRepository.findBrandsByPetFoodName(petFoodName);
        System.out.println("==> findBrandsByPetFoodName");
        System.out.println(findBrandsByPetFoodName);
        return findBrandsByPetFoodName;
    }

    @Transactional
    public void testJoin() {
        Brand findBrandsByName = findBrandsByName("베베");
        Brand findBrandsByPetFoodName = findBrandsByPetFoodName("사료");
        System.out.println(findBrandsByName);
        System.out.println(findBrandsByPetFoodName);
    }
}
