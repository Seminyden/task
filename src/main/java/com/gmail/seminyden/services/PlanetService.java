package com.gmail.seminyden.services;

import com.gmail.seminyden.model.Planet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PlanetService {

    Page<Planet> getAllPlanets(Pageable pageable);

    Planet createPlanet(Planet planet);

    Optional<Planet> updatePlanet(Long id, Planet planet);

    Optional<Planet> deletePlanet(Long id);
}