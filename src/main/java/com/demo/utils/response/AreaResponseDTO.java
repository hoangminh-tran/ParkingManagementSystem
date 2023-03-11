package com.demo.utils.response;

import lombok.Data;

@Data
public class AreaResponseDTO {

    private Long Id_Area; // Generate value

    private String Type_of_area; // R or C

    private Integer Number_Of_Slot; // 20 slot for each area(customer, resident)

    private String area_name; // name of area (resident area or customer area)

    private BuildingResponseDTO building;
}
