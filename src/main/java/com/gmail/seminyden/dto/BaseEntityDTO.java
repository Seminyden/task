package com.gmail.seminyden.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.seminyden.dto.view.View;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public abstract class BaseEntityDTO {

    @JsonView({View.Lord.class,
               View.Planet.class})
    private Long id;
}