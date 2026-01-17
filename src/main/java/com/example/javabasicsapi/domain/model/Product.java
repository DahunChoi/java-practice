package com.example.javabasicsapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class Product {

    private Long id;

    @Setter
    private String name;

    @Setter
    private BigDecimal price;

    @Setter
    private int stock;
}
