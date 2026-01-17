package com.example.javabasicsapi.infra.repository;

import com.example.javabasicsapi.domain.model.Product;
import com.example.javabasicsapi.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    private final Map<Long, Product> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Product save(Product product) {
        // id가 없으면 생성
        if (product.getId() == null) {
            Long id = sequence.incrementAndGet();
            Product saved = Product.builder()
                    .id(id)
                    .name(product.getName())
                    .price(product.getPrice())
                    .stock(product.getStock())
                    .build();
            store.put(id, saved);
            return saved;
        }
        // id가 있으면 갱신
        store.put(product.getId(), product);
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return store.containsKey(id);
    }
}
