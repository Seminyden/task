package com.gmail.seminyden.mappers;

import com.gmail.seminyden.dto.PlanetDTO;
import com.gmail.seminyden.model.Planet;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LordMapper.class})
public interface PlanetMapper {

    Planet toEntity(PlanetDTO planetDTO, @Context CycleAvoidingMappingContext context);

    PlanetDTO toDTO(Planet planet, @Context CycleAvoidingMappingContext context);
}