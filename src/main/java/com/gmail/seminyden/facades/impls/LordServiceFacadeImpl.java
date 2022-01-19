package com.gmail.seminyden.facades.impls;

import com.gmail.seminyden.dto.LordDTO;
import com.gmail.seminyden.facades.LordServiceFacade;
import com.gmail.seminyden.mappers.CycleAvoidingMappingContext;
import com.gmail.seminyden.mappers.LordMapper;
import com.gmail.seminyden.model.Lord;
import com.gmail.seminyden.services.LordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class LordServiceFacadeImpl implements LordServiceFacade {

    private LordService lordService;
    private LordMapper lordMapper;

    @Autowired
    public LordServiceFacadeImpl(LordService lordService,
                                 LordMapper lordMapper) {
        this.lordService = lordService;
        this.lordMapper = lordMapper;
    }


    @Override
    public LordDTO createLord(LordDTO lordDTO) {
        return toDTO(lordService.createLord(toEntity(lordDTO)));
    }


    @Override
    public Page<LordDTO> getAllLords(Pageable pageable) {
        return lordService
                .getAllLords(pageable)
                .map(this::toDTO);
    }


    @Override
    public Page<LordDTO> getAllLordsWithoutPlanets(Pageable pageable) {
        return lordService
                .getAllLordsWithoutPlanets(pageable)
                .map(this::toDTO);
    }


    @Override
    public Page<LordDTO> getTopTenYoungestLords(Pageable pageable) {
        return lordService
                .getTenYoungestLords(pageable)
                .map(this::toDTO);
    }


    private Lord toEntity(LordDTO lordDTO) {
        return lordMapper.toEntity(lordDTO, new CycleAvoidingMappingContext());
    }


    private LordDTO toDTO(Lord lord) {
        return lordMapper.toDTO(lord, new CycleAvoidingMappingContext());
    }
}