package com.example.javabasicsapi.application.service;

public abstract class AbstractCrudService {

    protected RuntimeException notFound(String message) {
        return new IllegalArgumentException(message);
    }

    protected void require(boolean condition, String message) {
        if (!condition) throw new IllegalArgumentException(message);
    }
}
