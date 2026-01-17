package com.example.javabasicsapi.application.dto;

import com.example.javabasicsapi.domain.model.Product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal price,
        int stock
) {
    public static ProductResponse from(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getStock()
        );
    }
}
