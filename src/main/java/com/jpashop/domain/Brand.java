package com.jpashop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Brand {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "brandId")
    List<PetFood> petFoods;
}
