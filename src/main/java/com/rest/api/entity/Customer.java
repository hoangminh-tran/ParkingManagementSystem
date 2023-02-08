package com.rest.api.entity;

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

    @OneToMany(mappedBy = "customer")
    private List<Booking> list_Booking;
}
