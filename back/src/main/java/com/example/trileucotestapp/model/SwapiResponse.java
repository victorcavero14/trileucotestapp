package com.example.trileucotestapp.model;

import java.util.List;

import lombok.Data;

@Data
public class SwapiResponse {
    private List<SwapiPerson> results;
}
