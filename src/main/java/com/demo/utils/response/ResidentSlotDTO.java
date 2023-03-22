package com.demo.utils.response;

import lombok.Data;

@Data
public class ResidentSlotDTO {
    private String Id_R_Slot;

    private String Type_Of_Vehicle;

    private boolean Status_Slots;

    private Long Id_Area;
}
