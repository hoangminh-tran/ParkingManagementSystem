package com.rest.api.utils.request;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private String id;

    private String fullname;

    private String password;

    private boolean gender;

    private Date dateofbirth;

    private String email;

    private String phone;
}
