package com.demo.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer_Slot_Response_DTO {
    private String Id_Building;

    private String Id_C_Slot;

    private boolean Status_Slots;
}
