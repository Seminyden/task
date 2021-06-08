package com.gmail.seminyden.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.seminyden.dto.LordDTO;
import com.gmail.seminyden.dto.view.JsonViewPage;
import com.gmail.seminyden.dto.view.View;
import com.gmail.seminyden.facades.LordServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lords")
@CrossOrigin(originPatterns = "*")
public class LordController {

    private LordServiceFacade lordServiceFacade;

    @Autowired
    public LordController(LordServiceFacade lordServiceFacade) {
        this.lordServiceFacade = lordServiceFacade;
    }


    @GetMapping
    @JsonView(View.Lord.class)
    public ResponseEntity<JsonViewPage<LordDTO>> getAllLords(@PageableDefault Pageable pageable) {
        JsonViewPage<LordDTO> page = new JsonViewPage<>(lordServiceFacade.getAllLords(pageable));
        return page.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(page);
    }


    @GetMapping("/without_planets")
    @JsonView(View.Lord.class)
    public ResponseEntity<JsonViewPage<LordDTO>> getAllLordsWithoutPlanets(@PageableDefault Pageable pageable) {
        JsonViewPage<LordDTO> page = new JsonViewPage<>(lordServiceFacade.getAllLordsWithoutPlanets(pageable));
        return page.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(page);
    }


    @GetMapping("/youngest")
    @JsonView(View.Lord.class)
    public ResponseEntity<JsonViewPage<LordDTO>> getTopTenYoungestLords(@PageableDefault Pageable pageable) {
        JsonViewPage<LordDTO> page = new JsonViewPage<>(lordServiceFacade.getTopTenYoungestLords(pageable));
        return page.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(page);
    }


    @PostMapping
    @JsonView(View.Lord.class)
    public ResponseEntity<LordDTO> createLord(@RequestBody LordDTO lordDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lordServiceFacade.createLord(lordDTO));
    }
}