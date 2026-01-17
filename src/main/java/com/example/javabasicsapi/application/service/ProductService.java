package com.example.javabasicsapi.application.service;

import com.example.javabasicsapi.application.dto.ProductCreateRequest;
import com.example.javabasicsapi.application.dto.ProductResponse;
import com.example.javabasicsapi.application.dto.ProductUpdateRequest;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductCreateRequest req);
    ProductResponse getById(Long id);
    List<ProductResponse> getAll();
    ProductResponse update(Long id, ProductUpdateRequest req);
    void delete(Long id);
}
