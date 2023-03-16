package com.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Manager")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manager{
    @Id
    @Column(name = "Id_Manager", unique = false)
    private String IdUser;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "Id_Manager", referencedColumnName = "Id_User", insertable = false, updatable = false)
    private User user;

    @Column(name = "Role")
    private int Role;
    // role 3 : security
    // role 2: manager Area
    // role 1: Manager
    @Column(name = "Status_Account")
    private boolean Status_Account;

    public Manager(String idUser, User user, int role) {
        IdUser = idUser;
        this.user = user;
        Role = role;
        this.Status_Account = false;
    }

    @OneToOne(mappedBy = "manager")
    private Building building;
}
