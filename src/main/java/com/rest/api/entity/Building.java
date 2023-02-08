package com.rest.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Building", uniqueConstraints = @UniqueConstraint(columnNames = "Id_Building"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Building {
    @Id
    @Column(name = "Id_Building")
    private String Id_Building;

    @Column(name = "Number_Of_Area")
    private Integer Number_Of_Area;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Manager", referencedColumnName = "Id_Manager")
    private Manager manager;

    @OneToMany(mappedBy = "area")
    private List<Area> listArea;
}
