package com.gmail.seminyden.repositories;

import com.gmail.seminyden.model.Lord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LordRepository extends JpaRepository<Lord, Long> {

    Page<Lord> findAllLordsByPlanetsIsNull(Pageable pageable);

    Page<Lord> findTop10ByOrderByAgeAsc(Pageable pageable);
}