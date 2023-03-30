package com.demo.service.Impl;

import com.demo.entity.*;
import com.demo.repository.*;
import com.demo.service.BuildingManagerService;
import com.demo.utils.request.RevenueDTO;
import com.demo.utils.request.SecurityDTO;
import com.demo.utils.request.UpdateDTO;
import com.demo.utils.response.BuildingManagerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BuildingManagerServiceImpl implements BuildingManagerService{
    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    Invoice_C_Repository invoice_c_repository;

    @Autowired
    Invoice_R_Repository invoice_r_repository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    Payment_C_Repository payment_c_repository;

    @Autowired
    Payment_R_Repository payment_r_repository;

    @Autowired
    Resident_Slot_Repository resident_slot_repository;
    @Override
    public List<SecurityDTO> findAllSecurity() {
        List<User>list_security = userRepository.findSecurityByIdUser(3);
        List<SecurityDTO> list = new ArrayList<>();
        for(User user : list_security)
        {
            Manager manager = managerRepository.findById(user.getId()).get();
            list.add(new SecurityDTO(user.getId(), user.getFullname(), user.getPassword(), user.isGender(), user.getDateofbirth(),
                    user.getEmail(), user.getPhone(), manager.isStatus_Account(), manager.getRole()));
        }
        return list;
    }

    @Override
    public SecurityDTO BanOrUnbanSecurity(String idUser, boolean status) {
        System.out.println(idUser);
        System.out.println(status);
        User user = userRepository.findById(idUser).get();
        Manager manager = managerRepository.findById(idUser).get();
        manager.setIdUser(idUser);
        manager.setStatus_Account(status);
        managerRepository.save(manager);

        return new SecurityDTO(user.getId(), user.getFullname(), user.getPassword(), user.isGender(), user.getDateofbirth(),
                user.getEmail(), user.getPhone(), manager.isStatus_Account(), manager.getRole());
    }

    @Override
    public SecurityDTO updateSecurity(String idUser, UpdateDTO user) {
        User dto = userRepository.findSecurityByIdManager(idUser, 3);
        dto.setId(idUser);
        dto.setEmail(user.getEmail());
        dto.setGender(user.isGender());
        dto.setDateofbirth(user.getDateofbirth());
        dto.setPassword(user.getPassword());
        dto.setFullname(user.getFullname());
        dto.setPhone(user.getPhone());
        userRepository.save(dto);
        Manager manager = managerRepository.findById(idUser).get();
        return new SecurityDTO(idUser, user.getFullname(), user.getPassword(), user.isGender(), user.getDateofbirth(),
                user.getEmail(), user.getPhone(), manager.isStatus_Account(), manager.getRole());
    }

    @Override
    public String createSecurity(User user) {
        Manager security = managerRepository.findManagerByManagerRole(user.getId(), 3);
        if(security != null)
        {
            return "Security Account is exited in DB";
        }
        User dto = new User();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setGender(user.isGender());
        dto.setDateofbirth(user.getDateofbirth());
        dto.setPassword(user.getPassword());
        dto.setFullname(user.getFullname());
        dto.setPhone(user.getPhone());
        userRepository.save(dto);
        managerRepository.save(new Manager(user.getId(), userRepository.findById(user.getId()).get(), 3));
        return "Create Security Account Successfully";
    }

    @Override
    public List<RevenueDTO> RevenueFromEachBuilding() {
        List<Building>list = buildingRepository.findAll();
//        for (Building building : list)
//        {
//            System.out.println(building.getId_Building());
//        }
        List<RevenueDTO> revenueDTOList = new ArrayList<>();
        int id_Area = 0;
        for (Building building : list)
        {
            switch (building.getId_Building())
            {
                case "A":
                    id_Area = 4;
                    break;
                case "B":
                    id_Area = 5;
                    break;
                case "C":
                    id_Area = 6;
                    break;
            }
          //  System.out.println(building.getId_Building() + " " + building.getIncome());
          //   System.out.println(countCustomerPaymentFromEachBuilding(Long.parseLong(id_Area + "")));
                System.out.println(countResidentPaymentFromEachBuilding(building.getId_Building(), Long.parseLong((id_Area - 3) + "")));
            revenueDTOList.add(new RevenueDTO(building.getId_Building(), building.getIncome(),building.getManager().getIdUser(),
                    countCustomerPaymentFromEachBuilding(Long.parseLong(id_Area + "")),
                    countResidentPaymentFromEachBuilding(building.getId_Building(), Long.parseLong((id_Area - 3) + ""))));
        }
        return revenueDTOList;
    }

    private int countCustomerPaymentFromEachBuilding(Long id_Area)
    {
        int count = 0;
        List<Booking> bookings = bookingRepository.findIdBookingByIdArea(id_Area);
        Set<String> set = new HashSet<>();
        for(Booking booking : bookings)
        {
            if(!set.contains(booking.getCustomer().getIdUser()))
            {
                set.add(booking.getCustomer().getIdUser());
                if(payment_c_repository.findPayment_C_By_Id_Booking(booking.getId_Booking()) != null)
                {
                    Payment_C payment_c = payment_c_repository.findPayment_C_By_Id_Booking(booking.getId_Booking());
                    if(invoice_c_repository.findCustomer_Invoice_By_Id_Payment(payment_c.getId_Payment()) != null)
                    {
                        Customer_Invoice customer_invoice = invoice_c_repository.findCustomer_Invoice_By_Id_Payment(payment_c.getId_Payment());
                        if(customer_invoice.isStatus())
                        {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    private int countResidentPaymentFromEachBuilding(String id_Building, Long id_Area)
    {
        int count = 0;
        List<Resident_Slot> residentSlots = resident_slot_repository.findAllSlotOfEachBuilding(id_Building);
        Set<String> set = new HashSet<>();
        for(Resident_Slot residentSlot : residentSlots)
        {
            if(residentSlot.getResident() == null) continue;
            if(!set.contains(residentSlot.getResident().getIdUser()) && residentSlot.isStatus_Slots() == true)
            {
                if(payment_r_repository.findListPayment_R_By_ResidentSlotAndResidentName(residentSlot.getId_R_Slot(), id_Area, residentSlot.getResident().getIdUser()) != null)
                {
                     List<Payment_R> list = payment_r_repository.findListPayment_R_By_ResidentSlotAndResidentName(residentSlot.getId_R_Slot(), id_Area, residentSlot.getResident().getIdUser());
                     for (Payment_R paymentR : list)
                     {
//                         System.out.println(paymentR.getId_Payment() + " " + id_Building);
                         if(invoice_r_repository.findResident_InvoiceByResidentPayment(paymentR.getId_Payment()) != null)
                        {
                            Resident_Invoice residentInvoice = invoice_r_repository.findResident_InvoiceByResidentPayment(paymentR.getId_Payment());
                            if(residentInvoice.isStatus() && !set.contains(residentSlot.getResident().getIdUser()))
                            {
                                set.add(residentSlot.getResident().getIdUser());
                                count++;
                            }
                        }
                     }
                }
            }
        }
        return count;
    }

    @Override
    public SecurityDTO findByIdSecurity(String id_Manager) {
        Manager manager = managerRepository.findById(id_Manager).get();
        User user = userRepository.findSecurityByIdManager(id_Manager, 3);
        return new SecurityDTO(user.getId(), user.getFullname(), user.getPassword(), user.isGender(), user.getDateofbirth(),
                user.getEmail(), user.getPhone(), manager.isStatus_Account(), manager.getRole());
    }
}
