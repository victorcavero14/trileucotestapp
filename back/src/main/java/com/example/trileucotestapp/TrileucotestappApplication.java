package com.example.trileucotestapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TrileucotestappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrileucotestappApplication.class, args);
	}

}
