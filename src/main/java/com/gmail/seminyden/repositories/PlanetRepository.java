package com.gmail.seminyden.repositories;

import com.gmail.seminyden.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
}