package com.demo.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resident_Slot_Response_DTO {
    private String idUser; // i

    private String fullname;

    private String email;

    private String phone;

    private String id_Building;

    private String type_Of_Vehicle;

    private String Id_R_Slot;
}
