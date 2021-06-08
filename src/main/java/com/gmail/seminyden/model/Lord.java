package com.gmail.seminyden.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "lords")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"name", "age"})
public class Lord extends BaseEntity {

    @NotBlank(message = "Lord name mustn't be blank")
    @Column(name = "name", nullable = false)
    private String name;

    @Positive(message = "Lord age must be greater than 0")
    @NotNull(message = "Lord age mustn't be null")
    @Column(name = "age", nullable = false)
    private Long age;

    @OneToMany(mappedBy = "lord")
    private List<Planet> planets;
}