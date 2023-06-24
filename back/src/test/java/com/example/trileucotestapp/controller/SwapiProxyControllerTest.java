package com.example.trileucotestapp.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.trileucotestapp.dto.PersonInfo;
import com.example.trileucotestapp.service.SwapiProxyService;

class SwapiProxyControllerTest {

    @Mock
    private SwapiProxyService swapiProxyService;

    @InjectMocks
    private SwapiProxyController swapiProxyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPersonInfo_ExistingName() {
        String name = "Luke Skywalker";
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(name);
        when(swapiProxyService.getPersonInfo(name)).thenReturn(personInfo);

        ResponseEntity<PersonInfo> response = swapiProxyController.getPersonInfo(name);

        verify(swapiProxyService, times(1)).getPersonInfo(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personInfo, response.getBody());
    }

    @Test
    void testGetPersonInfo_NonExistingName() {
        String name = "Non-existing character";
        when(swapiProxyService.getPersonInfo(name)).thenReturn(null);

        ResponseEntity<PersonInfo> response = swapiProxyController.getPersonInfo(name);

        verify(swapiProxyService, times(1)).getPersonInfo(name);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}
