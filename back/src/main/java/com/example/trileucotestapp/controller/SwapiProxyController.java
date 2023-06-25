package com.example.trileucotestapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.trileucotestapp.dto.PersonInfo;
import com.example.trileucotestapp.service.SwapiProxyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SwapiProxyController {

    @Autowired
    private SwapiProxyService swapiProxyService;

    /**
     * Retrieves information about a Star Wars character by their name.
     *
     * @param name the name of the character.
     * @return a ResponseEntity containing a PersonInfo object (if found), and an
     *         HTTP status code.
     */
    @Operation(summary = "Retrieve Star Wars character information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the information"),
            @ApiResponse(responseCode = "404", description = "The character is not found")
    })
    @GetMapping("/swapi-proxy/person-info")
    public ResponseEntity<PersonInfo> getPersonInfo(@RequestParam("name") String name) {
        PersonInfo personInfo = swapiProxyService.getPersonInfo(name);

        if (personInfo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(personInfo, HttpStatus.OK);
    }
}
