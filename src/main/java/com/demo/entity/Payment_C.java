package com.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Customer_Invoice customer_invoice;

    @OneToOne()
    @JoinColumn(name = "Id_Booking")
    private Booking booking;

    public Payment_C(String id_Payment, String type, Booking booking) {
        Id_Payment = id_Payment;
        Type = type;
        this.booking = booking;
    }
}
