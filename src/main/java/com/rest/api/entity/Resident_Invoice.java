package com.rest.api.entity;

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

    @Column(name = "Total_Of_Money")
    private Integer Total_Of_Money;

    @Column(name = "Time")
    private Date Time;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Payment", referencedColumnName = "Id_Payment")
    private Payment_R payment_r;

}
