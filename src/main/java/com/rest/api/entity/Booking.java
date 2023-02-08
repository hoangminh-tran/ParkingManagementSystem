package com.rest.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Booking", uniqueConstraints = @UniqueConstraint(columnNames = "Id_Booking"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @Column(name = "Id_Booking")
    private String Id_Booking;

    @Column(name = "Time")
    private Date Time;

    @ManyToOne()
    @JoinColumn(name = "Id_C_Slot")
    private Customer_Slot customer_slot;

    @ManyToOne()
    @JoinColumn(name = "Id_Customer")
    private Customer customer;

    @OneToOne(mappedBy = "booking")
    private Payment_C payment_c;
}