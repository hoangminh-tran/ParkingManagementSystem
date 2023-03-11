package com.demo.utils.response;

import lombok.Data;

@Data
public class ResidentResponseDTO {
    private String IdUser;

    private UserResponseDTO user;

    private boolean Status_Account;
}