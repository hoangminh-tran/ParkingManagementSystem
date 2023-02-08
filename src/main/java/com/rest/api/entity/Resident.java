package com.rest.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Resident")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resident {
    @Id
    @Column(name = "Id_Resident")
    private String IdUser;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "Id_Resident", referencedColumnName = "Id_User", insertable = false, updatable = false)
    private User user;

    @Column(name = "Status_Account")
    private boolean Status_Account;

    @OneToOne(mappedBy = "resident")
    private Payment_R payment_r;

    @OneToOne(mappedBy = "resident")
    private Resident_Slot residentSlot;
}
