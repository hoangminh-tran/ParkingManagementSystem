package com.demo.service.Impl;

import com.demo.entity.Area;
import com.demo.entity.Customer_Slot;
import com.demo.entity.Resident_Slot;
import com.demo.repository.AreaRepository;
import com.demo.repository.Customer_Slot_Repository;
import com.demo.repository.Resident_Slot_Repository;
import com.demo.service.PresentSlotOfEachBuilding;
import com.demo.utils.response.PresentSlotResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PresentSlotOfEachBuildingImpl implements PresentSlotOfEachBuilding {
    @Autowired
    Customer_Slot_Repository customer_slot_repository;

    @Autowired
    Resident_Slot_Repository resident_slot_repository;

    @Autowired
    AreaRepository areaRepository;

    @Override
    public List<PresentSlotResponseDto> findAll(String id_Building) {
        List<PresentSlotResponseDto> list = new ArrayList<>();
        List<Customer_Slot> listCustomerSlot = customer_slot_repository.findAllSlotOfEachBuilding(id_Building);
        List<Resident_Slot> listResidentSlot = resident_slot_repository.findAllSlotOfEachBuilding(id_Building);
        for(int i = 0; i < listCustomerSlot.size(); i++)
        {
            Customer_Slot customerSlot = listCustomerSlot.get(i);
            list.add(new PresentSlotResponseDto(customerSlot.getId_C_Slot(), id_Building, customerSlot.isStatus_Slots()));
        }
        for(int i = 0; i < listResidentSlot.size(); i++)
        {
            Resident_Slot residentSlot = listResidentSlot.get(i);
            list.add(new PresentSlotResponseDto(residentSlot.getId_R_Slot(), id_Building, residentSlot.isStatus_Slots()));
        }   
        return list;
    }
}
