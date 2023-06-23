package com.example.trileucotestapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.trileucotestapp.dto.FilmInfo;
import com.example.trileucotestapp.dto.PersonInfo;
import com.example.trileucotestapp.model.SwapiFilm;
import com.example.trileucotestapp.model.SwapiPerson;
import com.example.trileucotestapp.model.SwapiPlanet;
import com.example.trileucotestapp.model.SwapiResponse;
import com.example.trileucotestapp.model.SwapiVehicle;

@RestController
public class SwapiProxyController {

    private final RestTemplate restTemplate;

    @Autowired
    public SwapiProxyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/swapi-proxy/person-info")
    public ResponseEntity<?> getPersonInfo(@RequestParam("name") String name) {
        // String swapiUrl = "http://swapi.trileuco.com:1138/api/people/?search=" +
        // name;
        String swapiUrl = "https://swapi.dev/api/people/?search=" + name;
        SwapiResponse swapiResponse = restTemplate.getForObject(swapiUrl, SwapiResponse.class);

        if (swapiResponse == null || swapiResponse.getResults().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SwapiPerson swapiPerson = swapiResponse.getResults().get(0);

        // Obtener el planeta
        String planetUrl = swapiPerson.getHomeworld();
        SwapiPlanet swapiPlanet = restTemplate.getForObject(planetUrl, SwapiPlanet.class);

        // Obtener las películas
        List<String> filmUrls = swapiPerson.getFilms();
        List<SwapiFilm> swapiFilms = filmUrls.stream()
                .map(url -> restTemplate.getForObject(url, SwapiFilm.class))
                .collect(Collectors.toList());

        // Obtener el vehículo más rápido
        String fastestVehicleDriven = getFastestVehicleDriven(swapiPerson.getVehicles());

        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(swapiPerson.getName());
        personInfo.setBirth_year(swapiPerson.getBirth_year());
        personInfo.setGender(swapiPerson.getGender());
        personInfo.setPlanet_name(swapiPlanet.getName());
        personInfo.setFastest_vehicle_driven(fastestVehicleDriven);
        personInfo.setFilms(swapiFilms.stream()
                .map(film -> new FilmInfo(film.getTitle(), film.getRelease_date()))
                .collect(Collectors.toList()));

        return ResponseEntity.ok(personInfo);
    }

    private String getFastestVehicleDriven(List<String> vehicleUrls) {
        if (vehicleUrls == null || vehicleUrls.isEmpty()) {
            return null;
        }

        List<SwapiVehicle> swapiVehicles = vehicleUrls.stream()
                .map(url -> restTemplate.getForObject(url, SwapiVehicle.class))
                .collect(Collectors.toList());

        SwapiVehicle fastestVehicle = swapiVehicles.stream()
                .max((v1, v2) -> Float.compare(v1.getMaxAtmospheringSpeed(), v2.getMaxAtmospheringSpeed()))
                .orElse(null);

        return fastestVehicle != null ? fastestVehicle.getName() : null;
    }
}
