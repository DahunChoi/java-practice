package com.example.javabasicsapi.application.service;

import com.example.javabasicsapi.application.dto.ProductCreateRequest;
import com.example.javabasicsapi.application.dto.ProductResponse;
import com.example.javabasicsapi.application.dto.ProductUpdateRequest;
import com.example.javabasicsapi.config.CacheConfig;
import com.example.javabasicsapi.domain.model.Product;
import com.example.javabasicsapi.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends AbstractCrudService implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse create(ProductCreateRequest req) {
        Product saved = productRepository.save(Product.builder()
                .id(null)
                .name(req.name())
                .price(req.price())
                .stock(req.stock())
                .build());

        return ProductResponse.from(saved);
    }

    @Override
    @Cacheable(
            value = CacheConfig.PRODUCTS_CACHE,
            key = "#id",
            unless = "#result == null"
    )
    public ProductResponse getById(Long id) {
        log.info("[DB HIT] getById called. id={}", id);

        Product p = productRepository.findById(id)
                .orElseThrow(() -> notFound("Product not found. id=" + id));

        return ProductResponse.from(p);
    }

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream()
                .map(ProductResponse::from)
                .toList();
    }

    @Override
    @CachePut(value = CacheConfig.PRODUCTS_CACHE, key = "#id")
    public ProductResponse update(Long id, ProductUpdateRequest req) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> notFound("Product not found. id=" + id));

        p.setName(req.name());
        p.setPrice(req.price());
        p.setStock(req.stock());

        Product saved = productRepository.save(p);
        return ProductResponse.from(saved);
    }

    @Override
    @CacheEvict(value = CacheConfig.PRODUCTS_CACHE, key = "#id")
    public void delete(Long id) {
        require(productRepository.existsById(id),
                "Product not found. id=" + id);

        productRepository.deleteById(id);
    }
}
