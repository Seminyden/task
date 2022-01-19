package com.gmail.seminyden.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.seminyden.dto.PlanetDTO;
import com.gmail.seminyden.dto.view.JsonViewPage;
import com.gmail.seminyden.dto.view.View;
import com.gmail.seminyden.facades.PlanetServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planets")
@CrossOrigin(originPatterns = "*")
public class PlanetController {

    private PlanetServiceFacade planetServiceFacade;

    @Autowired
    public PlanetController(PlanetServiceFacade planetServiceFacade) {
        this.planetServiceFacade = planetServiceFacade;
    }


    @GetMapping
    @JsonView(View.Planet.class)
    public ResponseEntity<JsonViewPage<PlanetDTO>> getAllPlanets(@PageableDefault Pageable pageable) {
        JsonViewPage<PlanetDTO> page = new JsonViewPage<>(planetServiceFacade.getAllPlanets(pageable));
        return page.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(page);
    }


    @PostMapping
    @JsonView(View.Planet.class)
    public ResponseEntity<PlanetDTO> createPlanet(@RequestBody PlanetDTO planetDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(planetServiceFacade.createPlanet(planetDTO));
    }


    @PutMapping("/{id}")
    @JsonView(View.Planet.class)
    public ResponseEntity<PlanetDTO> updatePlanet(@PathVariable("id") Long id,
                                                  @RequestBody PlanetDTO planetDTO) {
        return ResponseEntity.of(planetServiceFacade.updatePlanet(id, planetDTO));
    }


    @DeleteMapping("/{id}")
    @JsonView(View.Planet.class)
    public ResponseEntity<PlanetDTO> deletePlanet(@PathVariable("id") Long id) {
        return ResponseEntity.of(planetServiceFacade.deletePlanet(id));
    }
}