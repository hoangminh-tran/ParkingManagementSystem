package com.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Resident_Slot")
@Data
@NoArgsConstructor
public class Resident_Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Index")
    private Long index;

    @Column(name = "Id_R_Slot")
    private String Id_R_Slot;

    @Column(name = "Type_Of_Vehicle")
    private String Type_Of_Vehicle;

    @Column(name = "Status_Slots")
    private boolean Status_Slots;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Resident", referencedColumnName = "Id_Resident", unique = false, nullable = true)
    private Resident resident;

    public Resident_Slot(Long index, String id_R_Slot, String type_Of_Vehicle, boolean status_Slots, Resident resident, Area area) {
        this.index = index;
        Id_R_Slot = id_R_Slot;
        Type_Of_Vehicle = type_Of_Vehicle;
        Status_Slots = status_Slots;
        this.resident = resident;
        this.area = area;
    }

    public Resident_Slot(Long index, String id_R_Slot, String type_Of_Vehicle, boolean status_Slots, Area area) {
        this.index = index;
        Id_R_Slot = id_R_Slot;
        Type_Of_Vehicle = type_Of_Vehicle;
        Status_Slots = status_Slots;
        this.area = area;
    }

    public Resident_Slot(String id_R_Slot, String type_Of_Vehicle, boolean status_Slots, Area area) {
        Id_R_Slot = id_R_Slot;
        Type_Of_Vehicle = type_Of_Vehicle;
        Status_Slots = status_Slots;
        this.area = area;
    }

    @ManyToOne
    @JoinColumn(name = "Id_Area")
    private Area area;
}
