package com.example.trileucotestapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.example.trileucotestapp.dto.FilmInfo;
import com.example.trileucotestapp.dto.PersonInfo;
import com.example.trileucotestapp.error.PersonNotFoundException;
import com.example.trileucotestapp.model.SwapiFilm;
import com.example.trileucotestapp.model.SwapiPerson;
import com.example.trileucotestapp.model.SwapiPlanet;
import com.example.trileucotestapp.model.SwapiResponse;
import com.example.trileucotestapp.model.SwapiVehicle;

@ExtendWith(MockitoExtension.class)
class SwapiProxyServiceTest {

    private static final String LUKE_SEARCH_URL = "https://swapi.dev/api/people/?search=Luke";
    private static final String PLANET_URL = "http://swapi.dev/api/planets/1/";
    private static final String FILM_URL_1 = "http://swapi.dev/api/films/1/";
    private static final String FILM_URL_2 = "http://swapi.dev/api/films/2/";
    private static final String VEHICLE_URL = "http://swapi.dev/api/vehicles/1/";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SwapiProxyService swapiProxyService;

    private SwapiPerson swapiPerson;

    @BeforeEach
    void setUp() {
        swapiPerson = createMockedSwapiPerson();

        SwapiResponse swapiResponse = new SwapiResponse();
        swapiResponse.setResults(Collections.singletonList(swapiPerson));

        lenient().when(restTemplate.getForObject(LUKE_SEARCH_URL, SwapiResponse.class)).thenReturn(swapiResponse);
        lenient().when(restTemplate.getForObject(PLANET_URL, SwapiPlanet.class)).thenReturn(createMockedSwapiPlanet());
        lenient().when(restTemplate.getForObject(FILM_URL_1, SwapiFilm.class)).thenReturn(createMockedSwapiFilm1());
        lenient().when(restTemplate.getForObject(FILM_URL_2, SwapiFilm.class)).thenReturn(createMockedSwapiFilm2());
        lenient().when(restTemplate.getForObject(VEHICLE_URL, SwapiVehicle.class))
                .thenReturn(createMockedSwapiVehicle());
    }

    @Test
    void whenPersonFound_thenReturnPersonInfo() {
        PersonInfo result = swapiProxyService.getPersonInfo("Luke");

        assertAll(
                () -> assertEquals("Luke Skywalker", result.getName()),
                () -> assertEquals("19BBY", result.getBirth_year()),
                () -> assertEquals("male", result.getGender()),
                () -> assertEquals("Tatooine", result.getPlanet_name()),
                () -> assertEquals("Snowspeeder", result.getFastest_vehicle_driven()));

        List<FilmInfo> films = result.getFilms();
        assertAll(
                () -> assertNotNull(films),
                () -> assertEquals(2, films.size()));

        FilmInfo film1 = films.get(0);
        assertAll(
                () -> assertEquals("A New Hope", film1.getName()),
                () -> assertEquals("1977-05-25", film1.getRelease_date()));

        FilmInfo film2 = films.get(1);
        assertAll(
                () -> assertEquals("The Empire Strikes Back", film2.getName()),
                () -> assertEquals("1980-05-17", film2.getRelease_date()));
    }

    @Test
    void whenPersonNotFound_thenThrowException() {
        SwapiResponse swapiResponse = new SwapiResponse();
        swapiResponse.setResults(Collections.emptyList());

        when(restTemplate.getForObject(any(String.class), eq(SwapiResponse.class))).thenReturn(swapiResponse);

        assertThrows(PersonNotFoundException.class, () -> swapiProxyService.getPersonInfo("Unknown person"));
    }

    private SwapiPerson createMockedSwapiPerson() {
        SwapiPerson person = new SwapiPerson();
        person.setName("Luke Skywalker");
        person.setBirth_year("19BBY");
        person.setGender("male");
        person.setHomeworld(PLANET_URL);
        person.setFilms(Arrays.asList(FILM_URL_1, FILM_URL_2));
        person.setVehicles(Collections.singletonList(VEHICLE_URL));
        return person;
    }

    private SwapiPlanet createMockedSwapiPlanet() {
        SwapiPlanet planet = new SwapiPlanet();
        planet.setName("Tatooine");
        return planet;
    }

    private SwapiFilm createMockedSwapiFilm1() {
        SwapiFilm film = new SwapiFilm();
        film.setTitle("A New Hope");
        film.setRelease_date("1977-05-25");
        return film;
    }

    private SwapiFilm createMockedSwapiFilm2() {
        SwapiFilm film = new SwapiFilm();
        film.setTitle("The Empire Strikes Back");
        film.setRelease_date("1980-05-17");
        return film;
    }

    private SwapiVehicle createMockedSwapiVehicle() {
        SwapiVehicle vehicle = new SwapiVehicle();
        vehicle.setName("Snowspeeder");
        vehicle.setMaxAtmospheringSpeed(650.0f);
        return vehicle;
    }
}
