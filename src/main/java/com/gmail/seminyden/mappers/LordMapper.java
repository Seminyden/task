package com.gmail.seminyden.mappers;

import com.gmail.seminyden.dto.LordDTO;
import com.gmail.seminyden.model.Lord;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PlanetMapper.class})
public interface LordMapper {

    Lord toEntity(LordDTO lordDTO, @Context CycleAvoidingMappingContext context);

    LordDTO toDTO(Lord lord, @Context CycleAvoidingMappingContext context);

    List<LordDTO> toDTOList(List<Lord> lordList, @Context CycleAvoidingMappingContext context);
}