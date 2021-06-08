package com.gmail.seminyden.facades;

import com.gmail.seminyden.dto.LordDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LordServiceFacade {

    LordDTO createLord(LordDTO lordDTO);

    Page<LordDTO> getAllLords(Pageable pageable);

    Page<LordDTO> getAllLordsWithoutPlanets(Pageable pageable);

    Page<LordDTO> getTopTenYoungestLords(Pageable pageable);
}