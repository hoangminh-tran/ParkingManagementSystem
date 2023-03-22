package com.demo.service.Impl;

import com.demo.entity.*;
import com.demo.repository.*;
import com.demo.service.PaymentResidentService;
import com.demo.utils.request.PaymentResidentDTO;
import com.demo.utils.response.PaymentResidentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.demo.entity.Money.*;

@Service
public class PaymentResidentServiceImpl implements PaymentResidentService {
    @Autowired
    ResidentRepository residentRepository;

    @Autowired
    Payment_R_Repository payment_r_repository;

    @Autowired
    Invoice_R_Repository invoice_r_repository;

    @Autowired
    Resident_Slot_Repository resident_slot_repository;

    @Autowired
    BuildingRepository buildingRepository;
    public PaymentResidentResponseDTO paymentResidentResponseDTO;

    public String message;

    @Override
    public PaymentResidentResponseDTO save(PaymentResidentDTO dto) {
        Resident resident = residentRepository.findById(dto.getIdUser()).get();
        if(resident.isStatus_Account() == true)
        {
            message = "Your Resident Account has been Banned or Not Longer Existed";
            return null;

        }
        List<Payment_R> list = payment_r_repository.findAll();
        Payment_R  payment_r = new Payment_R("PR" + (list.size() + 1), dto.getTypeOfPayment(), resident);
        payment_r_repository.save(payment_r);

        double Total_Of_Money = 0;

        // ko tim slot theo resident => tim theo cai "booking"

        Resident_Slot residentSlot = resident_slot_repository.findResidentSlotByIdResident(dto.getIdUser());
//        if(residentSlot.isStatus_Slots() == true)
//        {
//            message = "The slot is not empty you can not book that slot";
//            return null;
//        }
//        residentSlot.setStatus_Slots(true);
//        resident_slot_repository.save(residentSlot);
        String type_of_vehicle = residentSlot.getType_Of_Vehicle();
        switch(type_of_vehicle)
        {
            case "Car":
                Total_Of_Money += CAR_MONEY_BY_MONTH;
                break;
            case "Bike":
                Total_Of_Money += BIKE_MONEY_BY_MONTH;
                break;
            case "Motor":
                Total_Of_Money += MOTO_MONEY_BY_MONTH;
                break;
        }

        boolean status_Invoice = dto.getTypeOfPayment().equalsIgnoreCase("CASH") ? false : true;
        if (status_Invoice == true)
        {
            Building building = buildingRepository.findById(dto.getId_Building()).get();
            building.setIncome(building.getIncome() + Total_Of_Money);
            buildingRepository.save(building);
        }
        List<Resident_Invoice> list1 = invoice_r_repository.findAll();
        Resident_Invoice resident_invoice = new Resident_Invoice("RI" + (list1.size() + 1), status_Invoice, Total_Of_Money, dto.getDateOfPayment(), payment_r);
        resident.setResidentSlot(residentSlot);
        residentRepository.save(resident);
        invoice_r_repository.save(resident_invoice);

        paymentResidentResponseDTO = new PaymentResidentResponseDTO(dto.getIdUser(), "RI" + (list1.size() + 1), "PR" + (list.size() + 1),
                status_Invoice ,
                dto.getDateOfPayment(), Total_Of_Money, dto.getTypeOfPayment());
        message  = "Payment Resident Successfully";
        return paymentResidentResponseDTO;
    }

    @Override
    public PaymentResidentResponseDTO findPayment() {
        return paymentResidentResponseDTO;
    }

    @Override
    public String getMessageResidentPayment() {
        return message;
    }
}
