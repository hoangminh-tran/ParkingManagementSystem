package com.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Customer_Invoice", uniqueConstraints = @UniqueConstraint(columnNames = "Id_C_Invoice"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer_Invoice {
    @Id
    @Column(name = "Id_C_Invoice")
    private String Id_C_Invoice;

    @Column(name = "Total_Of_Money")
    private double Total_Of_Money;

    @Column(name = "Status")
    private boolean Status;

    @OneToOne()
    @JoinColumn(name = "Id_Payment")
    private Payment_C payment_c;
}