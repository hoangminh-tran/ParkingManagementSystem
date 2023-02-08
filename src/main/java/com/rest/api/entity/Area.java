package com.rest.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Area", uniqueConstraints = @UniqueConstraint(columnNames = "Id_Area"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Area {
    @Id
    @Column(name = "Id_Area")
    private String Id_Area;

    @Column(name = "Number_Of_Slot")
    private Integer Number_Of_Slot;

    @Column(name = "income")
    private Integer income;

    @Column(name = "type")
    private String type; // type of vehicle;

    @ManyToOne
    @JoinColumn(name = "Id_Building")
    private Building area;

    @OneToMany(mappedBy = "area")
    private List<Resident_Slot> List_resident_slot;

    @OneToMany(mappedBy = "area")
    private List<Customer_Slot> List_customer_slot;
}
