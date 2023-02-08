package com.rest.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Payment_C", uniqueConstraints = @UniqueConstraint(columnNames = "Id_Payment"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment_C {
    @Id
    @Column(name = "Id_Payment")
    private String Id_Payment;

    @Column(name =  "TypeOfPayment")
    private String Type; // Type of Payment

    @OneToOne(mappedBy = "payment_c")
    private Customer_Invoice customer_invoice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Booking")
    private Booking booking;
}
