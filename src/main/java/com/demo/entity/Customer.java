package com.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @Column(name = "Id_Customer")
    private String IdUser;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "Id_Customer", referencedColumnName = "Id_User", insertable = false, updatable = false)
    private User user;

    @Column(name = "Status_Account")
    private boolean Status_Account;

    @Column(name = "Cancel_Of_Payments")
    private int cancel_of_payments; // If user cancel order the cancel_of_payments + 1, if the cancel_of_payments >= 3 ban Account customer

    public Customer(String idUser, boolean status_Account, User user) {
        IdUser = idUser;
        this.user = user;
        Status_Account = status_Account;
        this.cancel_of_payments = 0;
    }

    @OneToMany(mappedBy = "customer", cascade =  CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Booking> list_Booking;
}