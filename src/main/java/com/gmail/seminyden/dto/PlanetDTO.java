package com.gmail.seminyden.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.seminyden.dto.view.View;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"name"})
public class PlanetDTO extends BaseEntityDTO {

    @JsonView({View.Planet.class,
               View.Lord.class})
    private String name;

    @JsonView({View.Planet.class})
    private LordDTO lord;
}