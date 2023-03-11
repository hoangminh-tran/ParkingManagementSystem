package com.demo.utils.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaAPI {
    private Long Id_Area;

    private String Type_of_area;

    private Integer Number_Of_Slot; // 20 slot for each area(customer, resident)

    private String area_name; // name of area (resident area or customer area)

    private String Id_Building;
}
