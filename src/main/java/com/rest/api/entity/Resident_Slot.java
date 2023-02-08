package com.rest.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Resident_Slot", uniqueConstraints = @UniqueConstraint(columnNames = "Id_R_Slot"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resident_Slot {
    @Id
    @Column(name = "Id_R_Slot")
    private String Id_R_Slot;

    @Column(name = "Type_Of_Vehicle")
    private Integer Type_Of_Vehicle;

    @Column(name = "Status_Slots")
    private boolean Status_Slots;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Resident", referencedColumnName = "Id_Resident")
    private Resident resident;

    @ManyToOne
    @JoinColumn(name = "Id_Area")
    private Area area;
}
