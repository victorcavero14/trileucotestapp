package com.example.trileucotestapp.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
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

    /**
     * Constructor for the SwapiProxyService class.
     *
     * @param restTemplate an instance of RestTemplate for making HTTP requests.
     */
    public SwapiProxyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieves comprehensive information about a Star Wars character by their
     * name.
     *
     * @param name the name of the character.
     * @return a PersonInfo object containing character details, their home planet,
     *         films they appear in, and fastest vehicle they've driven.
     */
    @Cacheable(value = "personinfo", key = "#name")
    public PersonInfo getPersonInfo(String name) {
        SwapiPerson swapiPerson = getSwapiPerson(name);
        String planetName = getPlanetName(swapiPerson);
        List<FilmInfo> films = getFilms(swapiPerson);
        String fastestVehicleDriven = getFastestVehicleDriven(swapiPerson.getVehicles());

        return mapToPersonInfo(swapiPerson, planetName, films, fastestVehicleDriven);
    }

    /**
     * Retrieves a SwapiPerson object based on the provided name.
     *
     * @param name The name of the person to retrieve.
     * @return A SwapiPerson object.
     * @throws PersonNotFoundException If the person is not found.
     */
    @Cacheable(value = "people", key = "#name")
    private SwapiPerson getSwapiPerson(String name) {
        String swapiUrl = "https://swapi.dev/api/people/?search=" + name;
        SwapiResponse swapiResponse = restTemplate.getForObject(swapiUrl, SwapiResponse.class);

        if (swapiResponse == null || swapiResponse.getResults().isEmpty()) {
            throw new PersonNotFoundException("Person with name " + name + " not found");
        }

        return swapiResponse.getResults().get(0);
    }

    /**
     * Retrieves the planet name of a SwapiPerson.
     *
     * @param swapiPerson The SwapiPerson object to get the planet name from.
     * @return The name of the planet.
     */
    @Cacheable(value = "planets", key = "#swapiPerson.homeworld")
    private String getPlanetName(SwapiPerson swapiPerson) {
        String planetUrl = swapiPerson.getHomeworld();
        SwapiPlanet swapiPlanet = restTemplate.getForObject(planetUrl, SwapiPlanet.class);

        /*
         * We could throw a new PlanetNotFoundException but is preferer to return
         * null instead
         */

        return swapiPlanet != null ? swapiPlanet.getName() : null;
    }

    /**
     * Retrieves the films of a SwapiPerson.
     *
     * @param swapiPerson The SwapiPerson object to get the films from.
     * @return A list of FilmInfo objects.
     */
    @Cacheable(value = "films", key = "#swapiPerson.name")
    private List<FilmInfo> getFilms(SwapiPerson swapiPerson) {
        return swapiPerson.getFilms().stream()
                .map(url -> restTemplate.getForObject(url, SwapiFilm.class))
                .filter(Objects::nonNull)
                .map(film -> new FilmInfo(film.getTitle(), film.getRelease_date()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the fastest vehicle driven by a SwapiPerson.
     *
     * @param vehicleUrls The URLs of the vehicles to get from the API.
     * @return The name of the fastest vehicle, or null if the person has not driven
     *         any vehicles.
     */
    @Cacheable(value = "vehicles", key = "#vehicleUrls")
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

    /**
     * Maps the fields of a SwapiPerson object and other parameters to a PersonInfo
     * object.
     *
     * @param swapiPerson          The SwapiPerson object to map from.
     * @param planetName           The name of the person's home planet.
     * @param films                A list of FilmInfo objects representing the films
     *                             the person has been in.
     * @param fastestVehicleDriven The name of the fastest vehicle the person has
     *                             driven.
     * @return A PersonInfo object with the mapped fields.
     */
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
