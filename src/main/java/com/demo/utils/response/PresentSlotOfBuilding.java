package com.demo.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresentSlotOfBuilding {
    private String Id_C_Slot;

    private String Type_Of_Vehicle;

    private boolean Status_Slots;

    private Long Id_Area;

    private String Type_of_area; // R or C

    private Integer Number_Of_Slot; // 20 slot for each area(customer, resident)

    private String area_name; // name of area (resident area or customer area)

    private String Id_Building;
}
