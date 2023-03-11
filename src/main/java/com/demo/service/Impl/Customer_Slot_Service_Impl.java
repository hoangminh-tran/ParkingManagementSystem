package com.demo.service.Impl;

import com.demo.entity.Area;
import com.demo.entity.Customer_Slot;
import com.demo.repository.AreaRepository;
import com.demo.repository.Customer_Slot_Repository;
import com.demo.service.Customer_Slot_Service;
import com.demo.utils.request.Customer_Slot_API;
import com.demo.utils.request.Customer_Slot_DTO;
import com.demo.utils.response.Customer_Slot_Response_DTO;
import com.demo.utils.response.PresentSlotOfBuilding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Customer_Slot_Service_Impl implements Customer_Slot_Service {

    @Autowired
    Customer_Slot_Repository customer_slot_repository;

    @Autowired
    AreaRepository areaRepository;

    @Override
    public List<Customer_Slot_Response_DTO> findAllSlotOfEachBuilding(String id_Building) {
        List<Customer_Slot_Response_DTO> list = new ArrayList<>();
        List<Customer_Slot> slotList = customer_slot_repository.findAllSlotOfEachBuilding(id_Building);

        for(int i = 0; i < slotList.size(); i++)
        {
            list.add(new Customer_Slot_Response_DTO(id_Building, slotList.get(i).getId_C_Slot(), slotList.get(i).isStatus_Slots()));
        }
        return list;
    }

    @Override
    public List<PresentSlotOfBuilding> findAllSlot(String id_Building) {
        List<Customer_Slot> slotList1 = customer_slot_repository.findAllSlotOfEachBuilding(id_Building);
        List<PresentSlotOfBuilding> list = new ArrayList<>();
        for(int i = 0; i < slotList1.size(); i++)
        {
            Area area = slotList1.get(i).getArea();
            list.add(new PresentSlotOfBuilding(slotList1.get(i).getId_C_Slot(), slotList1.get(i).getType_Of_Vehicle(),
                        slotList1.get(i).isStatus_Slots(), area.getId_Area(), area.getType_of_area(), area.getNumber_Of_Slot(),
                        area.getArea_name(), area.getBuilding().getId_Building()));
        }
        return list;
    }

    @Override
    public String save(Customer_Slot_DTO dto) {
        Customer_Slot customerSlotDto = new Customer_Slot(dto.getId_C_Slot(), dto.getType_Of_Vehicle(), dto.isStatus_Slots(), areaRepository.findById(dto.getId_Area()).get());
        customer_slot_repository.save(customerSlotDto);
        return "save successfull";
    }

    @Override
    public List<Customer_Slot_API> listAllCustomerSlot() {
        List<Customer_Slot> customer_slotList = customer_slot_repository.findAll();
        List<Customer_Slot_API>list = new ArrayList<>();
        for(Customer_Slot customerSlot : customer_slotList)
        {
            list.add(new Customer_Slot_API(customerSlot.getIndex(), customerSlot.getId_C_Slot(),
                    customerSlot.getType_Of_Vehicle(), customerSlot.isStatus_Slots(),
                    areaRepository.findById(customerSlot.getArea().getId_Area()).get().getBuilding().getId_Building()));
        }
        return list;
    }

    @Override
    public Customer_Slot_API findByIdCustomerSlot(Long id) {
        Customer_Slot customerSlot = customer_slot_repository.findById(id).get();
        return new Customer_Slot_API(customerSlot.getIndex(), customerSlot.getId_C_Slot(),
                customerSlot.getType_Of_Vehicle(), customerSlot.isStatus_Slots(),
                areaRepository.findById(customerSlot.getArea().getId_Area()).get().getBuilding().getId_Building());
    }
}
