package com.demo.utils.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resident_Slot_API {
    private Long Id_Index;

    private String Id_R_Slot;

    private String Type_Of_Vehicle;

    private boolean Status_Slots;

    private String id_Building;

    private String id_Resident;
}
