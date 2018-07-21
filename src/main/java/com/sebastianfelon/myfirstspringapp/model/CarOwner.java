package com.sebastianfelon.myfirstspringapp.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "car_owners")

public class CarOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String name;

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
