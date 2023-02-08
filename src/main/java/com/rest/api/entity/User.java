package com.rest.api.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private Date dateofbirth;

    @Column(name = "Email")
    private String email;

    @Column(name = "phone")
    private String phone;
}
