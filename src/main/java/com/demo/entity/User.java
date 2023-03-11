package com.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "Id_User")
    private String id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "DateOfBirth")
    @Temporal(TemporalType.DATE)
    private Date dateofbirth;

    @Column(name = "Email")
    private String email;

    @Column(name = "phone")
    private String phone;
}
