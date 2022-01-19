package com.gmail.seminyden.facades;

import com.gmail.seminyden.dto.PlanetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PlanetServiceFacade {

    Page<PlanetDTO> getAllPlanets(Pageable pageable);

    PlanetDTO createPlanet(PlanetDTO planetDTO);

    Optional<PlanetDTO> updatePlanet(Long id, PlanetDTO planetDTO);

    Optional<PlanetDTO> deletePlanet(Long id);
}