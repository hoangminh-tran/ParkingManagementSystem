package com.demo.utils.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;

    private String fullname;

    private String password;

    private boolean gender;

    private Date dateofbirth;

    private String email;

    private String phone;
}