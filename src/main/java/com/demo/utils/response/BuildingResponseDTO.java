package com.demo.utils.response;

import lombok.Data;


@Data
public class BuildingResponseDTO {
    private String Id_Building;

    private Integer Number_Of_Area;

    private double income;

    private ManagerResponseDTO manager;
}
