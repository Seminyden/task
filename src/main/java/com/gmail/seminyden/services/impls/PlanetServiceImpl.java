package com.gmail.seminyden.services.impls;

import com.gmail.seminyden.model.Lord;
import com.gmail.seminyden.model.Planet;
import com.gmail.seminyden.repositories.LordRepository;
import com.gmail.seminyden.repositories.PlanetRepository;
import com.gmail.seminyden.services.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PlanetServiceImpl implements PlanetService {

    private PlanetRepository planetRepository;
    private LordRepository lordRepository;

    @Autowired
    public PlanetServiceImpl(PlanetRepository planetRepository,
                             LordRepository lordRepository) {
        this.planetRepository = planetRepository;
        this.lordRepository = lordRepository;
    }


    @Override
    public Page<Planet> getAllPlanets(Pageable pageable) {
        return planetRepository.findAll(pageable);
    }


    @Override
    public Planet createPlanet(Planet planet) {
        checkPlanetValidity(planet);
        return planetRepository.save(planet);
    }


    @Override
    public Optional<Planet> updatePlanet(Long id, Planet planet) {
        Optional<Planet> planetToUpdate = planetRepository.findById(id);
        planetToUpdate.ifPresent(updatablePlanet -> {
            if (planet.getName() != null && !planet.getName().trim().isEmpty()) {
                updatablePlanet.setName(planet.getName());
            }
            if (planet.getLord() != null) {
                Lord lord = lordRepository.findById(planet.getLord().getId())
                                          .orElseThrow(() -> new IllegalArgumentException("Lord with id '" + planet.getLord().getId() + "' not found"));
                updatablePlanet.setLord(lord);
            }
            planetRepository.save(updatablePlanet);
        });
        return planetToUpdate;
    }


    @Override
    public Optional<Planet> deletePlanet(Long id) {
        Optional<Planet> planetToDelete = planetRepository.findById(id);
        planetToDelete.ifPresent(planet -> planetRepository.delete(planet));
        return planetToDelete;
    }


    private void checkPlanetValidity(Planet planet) {
        if (planet.getName() == null || planet.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Planet name mustn't be blank");
        }
    }
}