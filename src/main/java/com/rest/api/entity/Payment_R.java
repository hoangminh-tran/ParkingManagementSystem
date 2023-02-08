package com.rest.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Payment_R", uniqueConstraints = @UniqueConstraint(columnNames = "Id_Payment"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment_R {
    @Id
    @Column(name = "Id_Payment")
    private String Id_Payment;

    @Column(name =  "TypeOfPayment")
    private String Type; // Type of Payment

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Resident", referencedColumnName = "Id_Resident")
    private Resident resident;

    @OneToOne(mappedBy = "payment_r")
    private Resident_Invoice resident_invoice;
}
