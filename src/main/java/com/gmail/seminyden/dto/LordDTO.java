package com.gmail.seminyden.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.seminyden.dto.view.View;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"name", "age"})
public class LordDTO extends BaseEntityDTO {

    @JsonView({View.Lord.class,
               View.Planet.class})
    private String name;

    @JsonView({View.Lord.class,
               View.Planet.class})
    private Long age;

    @JsonView({View.Lord.class})
    private List<PlanetDTO> planets;
}