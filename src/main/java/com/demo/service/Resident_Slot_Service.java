package com.demo.service;

import com.demo.entity.Resident_Slot;
import com.demo.utils.request.Customer_Slot_API;
import com.demo.utils.request.Resident_Slot_API;
import com.demo.utils.request.Resident_Slot_DTO;
import com.demo.utils.response.ResidentSlotDTO;
import com.demo.utils.response.Resident_Slot_Response_DTO;

import java.util.List;

public interface Resident_Slot_Service {
    Resident_Slot_Response_DTO save(Resident_Slot_DTO dto);
    ResidentSlotDTO saveEmptySlot(ResidentSlotDTO dto);

    Resident_Slot_Response_DTO saveResidentSlot(Resident_Slot_DTO dto);
    Resident_Slot_Response_DTO find_Resident_Slot();

    List<Resident_Slot_API> listAllResidentSlot();

    Resident_Slot_API findByIdResidentSlot(Long id);

    String getMessageResidentBooking();
}
