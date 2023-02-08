package com.rest.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Customer_Slot", uniqueConstraints = @UniqueConstraint(columnNames = "Id_C_Slot"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer_Slot {
    @Id
    @Column(name = "Id_C_Slot")
    private String Id_C_Slot;

    @Column(name = "Type_Of_Vehicle")
    private String Type_Of_Vehicle;

    @Column(name = "Status_Slots")
    private boolean Status_Slots;

    @OneToMany(mappedBy = "customer_slot")
    private List<Booking> listBooking;

    @ManyToOne()
    @JoinColumn(name = "Id_Area")
    private Area area;
}