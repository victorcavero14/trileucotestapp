package com.example.trileucotestapp.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.trileucotestapp.dto.FilmInfo;
import com.example.trileucotestapp.dto.PersonInfo;
import com.example.trileucotestapp.error.PersonNotFoundException;
import com.example.trileucotestapp.model.SwapiFilm;
import com.example.trileucotestapp.model.SwapiPerson;
import com.example.trileucotestapp.model.SwapiPlanet;
import com.example.trileucotestapp.model.SwapiResponse;
import com.example.trileucotestapp.model.SwapiVehicle;

@Service
public class SwapiProxyService {

    private final RestTemplate restTemplate;

    public SwapiProxyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PersonInfo getPersonInfo(String name) {
        SwapiPerson swapiPerson = getSwapiPerson(name);
        String planetName = getPlanetName(swapiPerson);
        List<FilmInfo> films = getFilms(swapiPerson);
        String fastestVehicleDriven = getFastestVehicleDriven(swapiPerson.getVehicles());

        return mapToPersonInfo(swapiPerson, planetName, films, fastestVehicleDriven);
    }

    private SwapiPerson getSwapiPerson(String name) {
        String swapiUrl = "https://swapi.dev/api/people/?search=" + name;
        SwapiResponse swapiResponse = restTemplate.getForObject(swapiUrl, SwapiResponse.class);

        if (swapiResponse == null || swapiResponse.getResults().isEmpty()) {
            throw new PersonNotFoundException("Person with name " + name + " not found");
        }

        return swapiResponse.getResults().get(0);
    }

    private String getPlanetName(SwapiPerson swapiPerson) {
        String planetUrl = swapiPerson.getHomeworld();
        SwapiPlanet swapiPlanet = restTemplate.getForObject(planetUrl, SwapiPlanet.class);

        /*
         * Not necessary, I want to continue the execution even if the planet name is
         * missing
         * if (swapiPlanet == null) {
         * throw new PlanetNotFoundException("Planet with url " + planetUrl +
         * " not found");
         * }
         */

        return swapiPlanet != null ? swapiPlanet.getName() : null;
    }

    private List<FilmInfo> getFilms(SwapiPerson swapiPerson) {
        return swapiPerson.getFilms().stream()
                .map(url -> restTemplate.getForObject(url, SwapiFilm.class))
                .filter(Objects::nonNull)
                .map(film -> new FilmInfo(film.getTitle(), film.getRelease_date()))
                .collect(Collectors.toList());
    }

    private String getFastestVehicleDriven(List<String> vehicleUrls) {
        if (vehicleUrls == null || vehicleUrls.isEmpty()) {
            return null;
        }

        List<SwapiVehicle> swapiVehicles = vehicleUrls.stream()
                .map(url -> restTemplate.getForObject(url, SwapiVehicle.class))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        SwapiVehicle fastestVehicle = swapiVehicles.stream()
                .max((v1, v2) -> Float.compare(v1.getMaxAtmospheringSpeed(), v2.getMaxAtmospheringSpeed()))
                .orElse(null);

        return fastestVehicle != null ? fastestVehicle.getName() : null;
    }

    private PersonInfo mapToPersonInfo(SwapiPerson swapiPerson, String planetName, List<FilmInfo> films,
            String fastestVehicleDriven) {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(swapiPerson.getName());
        personInfo.setBirth_year(swapiPerson.getBirth_year());
        personInfo.setGender(swapiPerson.getGender());
        personInfo.setPlanet_name(planetName);
        personInfo.setFastest_vehicle_driven(fastestVehicleDriven);
        personInfo.setFilms(films);
        return personInfo;
    }
}
