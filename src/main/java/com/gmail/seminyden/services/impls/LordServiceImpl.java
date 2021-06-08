package com.gmail.seminyden.services.impls;

import com.gmail.seminyden.model.Lord;
import com.gmail.seminyden.repositories.LordRepository;
import com.gmail.seminyden.services.LordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class LordServiceImpl implements LordService {

    private LordRepository lordRepository;

    @Autowired
    public LordServiceImpl(LordRepository lordRepository) {
        this.lordRepository = lordRepository;
    }


    @Override
    public Lord createLord(Lord lord) {
        checkLordValidity(lord);
        return lordRepository.save(lord);
    }


    @Override
    public Page<Lord> getAllLords(Pageable pageable) {
        return lordRepository.findAll(pageable);
    }


    @Override
    public Page<Lord> getAllLordsWithoutPlanets(Pageable pageable) {
        return lordRepository.findAllLordsByPlanetsIsNull(pageable);
    }


    @Override
    public Page<Lord> getTenYoungestLords(Pageable pageable) {
        return lordRepository.findTop10ByOrderByAgeAsc(pageable);
    }


    private void checkLordValidity(Lord lord) {
        if (lord.getName() == null || lord.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Lord name mustn't be blank");
        }
        if (lord.getAge() == null || lord.getAge() <= 0) {
            throw new IllegalArgumentException("Lord age must be greater than 0");
        }
    }
}