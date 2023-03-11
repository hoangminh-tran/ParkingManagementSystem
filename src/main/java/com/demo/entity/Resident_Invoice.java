package com.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Resident_Invoice", uniqueConstraints = @UniqueConstraint(columnNames = "Id_R_Invoice"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resident_Invoice {
    @Id
    @Column(name = "Id_R_Invoice")
    private String Id_R_Invoice;

    @Column(name = "Status")
    private boolean Status;

    @Column(name = "Total_Of_Money")
    private double Total_Of_Money;

    @Column(name = "Time")
    @Temporal(TemporalType.DATE)
    private Date Time;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Payment", referencedColumnName = "Id_Payment")
    private Payment_R payment_r;

}
