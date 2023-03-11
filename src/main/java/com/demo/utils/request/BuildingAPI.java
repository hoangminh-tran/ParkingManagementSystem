package com.demo.utils.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingAPI {
    private String Id_Building;

    private Integer Number_Of_Area;

    private double income;

    private String id_Manager;
}
