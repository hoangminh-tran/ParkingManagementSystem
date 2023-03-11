package com.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Building")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Building {
    @Id
    @Column(name = "Id_Building")
    private String Id_Building;

    @Column(name = "Number_Of_Area")
    private Integer Number_Of_Area;

    @Column(name = "income")
    private double income;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "Id_Manager", referencedColumnName = "Id_Manager", unique = true)
    private Manager manager;

    public Building(String id_Building, Integer number_Of_Area, double income, Manager manager) {
        Id_Building = id_Building;
        Number_Of_Area = number_Of_Area;
        this.income = income;
        this.manager = manager;
    }

    @OneToMany(mappedBy = "building")
    @JsonIgnore
    private List<Area> listArea;
}
