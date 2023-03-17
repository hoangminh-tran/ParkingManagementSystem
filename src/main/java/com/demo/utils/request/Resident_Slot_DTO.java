package com.demo.utils.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resident_Slot_DTO {

    private String idUser; // i

    private String fullname;

    private String email;

    private String phone;

    private String id_Building;

    private String type_Of_Vehicle;

    private String Id_R_Slot;

//    private boolean status_Slots;
}
