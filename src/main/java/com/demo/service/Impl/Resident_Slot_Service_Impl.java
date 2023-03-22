package com.demo.service.Impl;

import com.demo.entity.Area;
import com.demo.entity.Customer_Slot;
import com.demo.entity.Resident;
import com.demo.entity.Resident_Slot;
import com.demo.repository.*;
import com.demo.service.Resident_Slot_Service;
import com.demo.utils.request.Customer_Slot_API;
import com.demo.utils.request.Resident_Slot_API;
import com.demo.utils.request.Resident_Slot_DTO;
import com.demo.utils.response.ResidentSlotDTO;
import com.demo.utils.response.Resident_Slot_Response_DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Resident_Slot_Service_Impl implements Resident_Slot_Service {
    @Autowired
    AreaRepository areaRepository;

    @Autowired
    Resident_Slot_Repository resident_slot_repository;

    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    ResidentRepository residentRepository;

    @Autowired
    UserRepository userRepository;

    public Resident_Slot_Response_DTO resident_slot_response_dto;

    public String message;

    @Override
    public Resident_Slot_Response_DTO save(Resident_Slot_DTO dto) {
        Resident resident = residentRepository.findById(dto.getIdUser()).get();

        Area area = areaRepository.findIdAreaByIdBuilding(dto.getId_Building(), "R");
        List<Resident_Slot> listslot = resident_slot_repository.findAll();
        Resident_Slot residentSlot = new Resident_Slot(Long.parseLong((listslot.size() +  1) + ""),
                dto.getId_R_Slot(), dto.getType_Of_Vehicle(), true, resident, area);
        resident.setResidentSlot(residentSlot);
        resident_slot_repository.save(residentSlot);

        resident_slot_response_dto = new Resident_Slot_Response_DTO(dto.getIdUser(), dto.getFullname(), dto.getEmail(), dto.getPhone(),
                dto.getId_Building(), dto.getType_Of_Vehicle(), dto.getId_R_Slot());

        return resident_slot_response_dto;
    }

    @Override
    public ResidentSlotDTO saveEmptySlot(ResidentSlotDTO dto) {
        List<Resident_Slot> list = resident_slot_repository.findAll();
        Long index = Long.parseLong(String.valueOf(list.size())) + 1;
//        Resident_Slot rs = new Resident_Slot(index, dto.getId_R_Slot(), dto.getType_Of_Vehicle(), dto.isStatus_Slots(), areaRepository.findById(dto.getId_Area()).get());
        Resident_Slot rs = new Resident_Slot(dto.getId_R_Slot(), dto.getType_Of_Vehicle(), dto.isStatus_Slots(), areaRepository.findById(dto.getId_Area()).get());
        resident_slot_repository.save(rs);
        return dto;
    }

    @Override
    public Resident_Slot_Response_DTO saveResidentSlot(Resident_Slot_DTO dto) {
        Resident resident = residentRepository.findById(dto.getIdUser()).orElse(null);
        if(resident.isStatus_Account() == true)
        {
            message = "Your Resident Account has been Banned or No Longer Existed!!!!";
            return null;
        }
        Resident_Slot resident_slot = resident_slot_repository.findResidentSlot(dto.getId_R_Slot(), dto.getId_Building());
        if(resident_slot.isStatus_Slots() == true)
        {
            message = "The Slot is not empty you can not book that slot";
            return null;
        }
        resident_slot.setId_R_Slot(dto.getId_R_Slot());
        resident_slot.setIndex(resident_slot.getIndex());
        resident_slot.setType_Of_Vehicle(dto.getType_Of_Vehicle());
        resident_slot.setStatus_Slots(true);
        resident_slot.setResident(resident);
        resident_slot.setArea(areaRepository.findIdAreaByIdBuilding(dto.getId_Building(), "R"));

        resident_slot_repository.save(resident_slot);

        resident_slot_response_dto = new Resident_Slot_Response_DTO(dto.getIdUser(), dto.getFullname(), dto.getEmail(), dto.getPhone(),
                dto.getId_Building(), dto.getType_Of_Vehicle(), dto.getId_R_Slot());
        message = "Resident Booking Slot Successfully";
        return resident_slot_response_dto;
    }

    @Override
    public Resident_Slot_Response_DTO find_Resident_Slot() {
        return resident_slot_response_dto;
    }

    @Override
    public String getMessageResidentBooking() {
        return message;
    }

    @Override
    public List<Resident_Slot_API> listAllResidentSlot() {
        List<Resident_Slot> residentSlotList = resident_slot_repository.findAll();
        List<Resident_Slot_API>list = new ArrayList<>();
        for(Resident_Slot resident_slot : residentSlotList)
        {
            list.add(new Resident_Slot_API(resident_slot.getIndex(), resident_slot.getId_R_Slot(),
                    resident_slot.getType_Of_Vehicle(), resident_slot.isStatus_Slots(),
                    areaRepository.findById(resident_slot.getArea().getId_Area()).get().getBuilding().getId_Building(),
                    resident_slot.isStatus_Slots() == true ? resident_slot.getResident().getIdUser() :  null));
        }
        return list;
    }
    @Override
    public Resident_Slot_API findByIdResidentSlot(Long id) {
        Resident_Slot resident_slot = resident_slot_repository.findById(id).get();
        return new Resident_Slot_API(resident_slot.getIndex(), resident_slot.getId_R_Slot(),
                resident_slot.getType_Of_Vehicle(), resident_slot.isStatus_Slots(),
                areaRepository.findById(resident_slot.getArea().getId_Area()).get().getBuilding().getId_Building(),
                resident_slot.isStatus_Slots() == true ? resident_slot.getResident().getIdUser() :  null);
    }
}