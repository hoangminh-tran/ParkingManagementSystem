package com.demo.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingManagerResponse {
    private String id;

    private String fullname;

    private String password;

    private boolean gender;

    private Date dateofbirth;

    private String email;

    private String phone;

    private boolean Status_Account;
}
