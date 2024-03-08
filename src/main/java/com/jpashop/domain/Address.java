package com.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable // Address 엔티티는 별도의 테이블을 가지지 않고 @Embedded private Address address; 와 같이 사용 되는 엔티티에 내장됩니다.
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
