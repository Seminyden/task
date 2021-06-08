package com.gmail.seminyden.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "planets")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"name"})
public class Planet extends BaseEntity {

    @NotBlank(message = "Planet name mustn't be blank")
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lord_id")
    private Lord lord;
}