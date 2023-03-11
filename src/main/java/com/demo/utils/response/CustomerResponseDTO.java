package com.demo.utils.response;

import lombok.Data;

@Data
public class CustomerResponseDTO {
    private String IdUser;

    private UserResponseDTO user;

    private boolean Status_Account;
}