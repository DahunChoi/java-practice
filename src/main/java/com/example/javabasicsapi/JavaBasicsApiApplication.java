package com.example.javabasicsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class JavaBasicsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaBasicsApiApplication.class, args);
	}

}
