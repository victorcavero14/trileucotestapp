package com.example.trileucotestapp.model;

import java.util.List;

import lombok.Data;

@Data
public class SwapiPerson {
    private String name;
    private String birth_year;
    private String gender;
    private String homeworld;
    private List<String> films;
    private List<String> vehicles;
}
