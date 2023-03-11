package com.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Payment_R")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment_R {
    @Id
    @Column(name = "Id_Payment")
    private String Id_Payment;

    @Column(name =  "TypeOfPayment")
    private String Type; // Type of Payment

    @OneToOne()
    @JoinColumn(name = "Id_Resident", referencedColumnName = "Id_Resident")
    private Resident resident;

    public Payment_R(String id_Payment, String type, Resident resident) {
        Id_Payment = id_Payment;
        Type = type;
        this.resident = resident;
    }

    @OneToOne(mappedBy = "payment_r")
    @JsonIgnore
    private Resident_Invoice resident_invoice;
}
