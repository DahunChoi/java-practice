package com.example.javabasicsapi.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {
    public static final String PRODUCTS_CACHE = "products";
    public static final String PRODUCT_LIST_CACHE = "productList";

    @Bean
    public CacheManager cacheManager() {
        // 기본 In-Memory 캐시 (ConcurrentHashMap 기반)
        return new ConcurrentMapCacheManager(PRODUCTS_CACHE, PRODUCT_LIST_CACHE);
    }
}
