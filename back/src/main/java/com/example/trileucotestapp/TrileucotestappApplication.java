package com.example.trileucotestapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TrileucotestappApplication {
	
	/**
     * The entry point of the Trileucotestapp application.
     *
     * @param args the command-line arguments.
     */
	public static void main(String[] args) {
		SpringApplication.run(TrileucotestappApplication.class, args);
	}

}
