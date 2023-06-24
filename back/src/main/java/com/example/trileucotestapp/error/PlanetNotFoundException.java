package com.example.trileucotestapp.error;

public class PlanetNotFoundException extends RuntimeException {
    public PlanetNotFoundException(String message) {
        super(message);
    }
}
