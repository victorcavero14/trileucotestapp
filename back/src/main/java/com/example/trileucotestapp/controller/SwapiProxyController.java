package com.example.trileucotestapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.trileucotestapp.dto.PersonInfo;
import com.example.trileucotestapp.service.SwapiProxyService;

@RestController
public class SwapiProxyController {

    @Autowired
    private SwapiProxyService swapiProxyService;

    @GetMapping("/swapi-proxy/person-info")
    public PersonInfo getPersonInfo(@RequestParam("name") String name) {
        return swapiProxyService.getPersonInfo(name);
    }
}
