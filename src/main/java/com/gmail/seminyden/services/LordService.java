package com.gmail.seminyden.services;

import com.gmail.seminyden.model.Lord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LordService {

    Lord createLord(Lord lord);

    Page<Lord> getAllLords(Pageable pageable);

    Page<Lord> getAllLordsWithoutPlanets(Pageable pageable);

    Page<Lord> getTenYoungestLords(Pageable pageable);
}