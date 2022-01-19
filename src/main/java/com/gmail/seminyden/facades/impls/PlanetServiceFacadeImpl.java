package com.gmail.seminyden.facades.impls;

import com.gmail.seminyden.dto.PlanetDTO;
import com.gmail.seminyden.facades.PlanetServiceFacade;
import com.gmail.seminyden.mappers.CycleAvoidingMappingContext;
import com.gmail.seminyden.mappers.PlanetMapper;
import com.gmail.seminyden.model.Planet;
import com.gmail.seminyden.services.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlanetServiceFacadeImpl implements PlanetServiceFacade {

    private PlanetService planetService;
    private PlanetMapper planetMapper;

    @Autowired
    public PlanetServiceFacadeImpl(PlanetService planetService,
                                   PlanetMapper planetMapper) {
        this.planetService = planetService;
        this.planetMapper = planetMapper;
    }


    @Override
    public Page<PlanetDTO> getAllPlanets(Pageable pageable) {
        return planetService
                .getAllPlanets(pageable)
                .map(this::toDTO);
    }


    @Override
    public PlanetDTO createPlanet(PlanetDTO planetDTO) {
        return toDTO(planetService.createPlanet(toEntity(planetDTO)));
    }


    @Override
    public Optional<PlanetDTO> updatePlanet(Long id, PlanetDTO planetDTO) {
        return planetService
                .updatePlanet(id, toEntity(planetDTO))
                .map(this::toDTO);
    }


    @Override
    public Optional<PlanetDTO> deletePlanet(Long id) {
        return planetService
                .deletePlanet(id)
                .map(this::toDTO);
    }


    private Planet toEntity(PlanetDTO planetDTO) {
        return planetMapper.toEntity(planetDTO, new CycleAvoidingMappingContext());
    }


    private PlanetDTO toDTO(Planet planet) {
        return planetMapper.toDTO(planet, new CycleAvoidingMappingContext());
    }
}