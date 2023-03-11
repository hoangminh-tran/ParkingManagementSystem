package com.demo.utils.response;

import com.demo.entity.Building;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Data
public class ManagerResponseDTO {
    private String IdUser;

    private UserResponseDTO user;

    private int Role;

    @JsonIgnore
    private Building building;
}
