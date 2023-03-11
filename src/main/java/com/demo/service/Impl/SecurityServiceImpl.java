package com.demo.service.Impl;

import com.demo.entity.*;
import com.demo.repository.*;
import com.demo.service.SecurityService;
import com.demo.utils.request.UpdateDTO;
import com.demo.utils.request.UserAPI;
import com.demo.utils.response.InvoiceCustomerResponse;
import com.demo.utils.response.InvoiceResidentResponse;
import com.demo.utils.response.ResponseCustomerInfoSlot;
import com.demo.utils.response.ResponseResidentInfoSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    Customer_Slot_Repository customer_slot_repository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    Resident_Slot_Repository resident_slot_repository;

    @Autowired
    Invoice_C_Repository invoice_c_repository;

    @Autowired
    Payment_C_Repository payment_c_repository;

    @Autowired
    Invoice_R_Repository invoice_r_repository;

    @Autowired
    Payment_R_Repository payment_r_repository;

    @Autowired
    ResidentRepository residentRepository;

    @Autowired
    CustomerRepository customerRepository;
    @Override
    public List<UserAPI> getAllCustomerFromBuilding(String Id_Building) {
        List<UserAPI> list = new ArrayList<>();
        Set<String> set = new HashSet<>();
        List<Customer_Slot> customer_slotList = customer_slot_repository.findAllSlotOfEachBuilding(Id_Building);
        for(Customer_Slot customerSlot : customer_slotList)
        {
            Booking booking = bookingRepository.findIdBookingByCustomerSlot(customerSlot.getId_C_Slot(), customerSlot.getArea().getId_Area());
//            System.out.println(booking);
            if(booking != null)
            {
                User user = userRepository.findById(booking.getCustomer().getIdUser()).get();
                if(!set.contains(booking.getCustomer().getIdUser()))
                {
                    Customer customer = customerRepository.findById(user.getId()).get();
                    list.add(new UserAPI(user.getId(), user.getFullname(), user.getPassword(), user.isGender(), user.getDateofbirth(),
                            user.getEmail(), user.getPhone(), customer.isStatus_Account()));
//                    System.out.println(user);
                    set.add(booking.getCustomer().getIdUser());
                }
            }
        }
        return list;
    }

    @Override
    public List<User> getAllResidentFromBuilding(String Id_Building) {
        List<User> list = new ArrayList<>();
        List<Resident_Slot> residentSlotList = resident_slot_repository.findAllSlotOfEachBuilding(Id_Building);
        for(Resident_Slot resident_slot : residentSlotList)
        {
            if(resident_slot.isStatus_Slots() == true)
            {
                User user = userRepository.findById(resident_slot.getResident().getIdUser()).get();
                list.add(user);
//                Customer customer = customerRepository.findById(user.getId()).get();
//                list.add(new UserAPI(user.getId(), user.getFullname(), user.getPassword(), user.isGender(), user.getDateofbirth(),
//                        user.getEmail(), user.getPhone(), customer.isStatus_Account()));
            }
        }
        return list;
    }

    @Override
    public InvoiceCustomerResponse searchCustomerInvoiceId(String Id_C_Invoice) {
        Customer_Invoice customer_invoice = invoice_c_repository.findById(Id_C_Invoice).get();

        Payment_C payment_c = payment_c_repository.findById(customer_invoice.getPayment_c().getId_Payment()).get();

        Booking booking = bookingRepository.findBookingByIdPayment(payment_c.getId_Payment());

        return new InvoiceCustomerResponse(customer_invoice.getId_C_Invoice(), payment_c.getId_Payment(), booking.getId_Booking(),
                customer_invoice.getTotal_Of_Money(), customer_invoice.isStatus(),
                payment_c.getType(), booking.getCustomer().getIdUser(), booking.getStartDate(), booking.getEndDate());
    }

    @Override
    public List<InvoiceCustomerResponse> searchCustomerInvoiceByCustomer(String id_Customer) {
        List<Booking> bookingList = bookingRepository.findBookingByCustomer(id_Customer);
        List<InvoiceCustomerResponse> list = new ArrayList<>();
        for(Booking booking : bookingList)
        {
            Payment_C payment_c = payment_c_repository.findPayment_C_By_Id_Booking(booking.getId_Booking());

            Customer_Invoice customer_invoice = invoice_c_repository.findCustomer_Invoice_By_Id_Payment(payment_c.getId_Payment());

            list.add(new InvoiceCustomerResponse(customer_invoice.getId_C_Invoice(), payment_c.getId_Payment(), booking.getId_Booking(),
                    customer_invoice.getTotal_Of_Money(), customer_invoice.isStatus(),
                    payment_c.getType(), booking.getCustomer().getIdUser(), booking.getStartDate(), booking.getEndDate()));
        }
        return list;
    }

    @Override
    public List<InvoiceCustomerResponse> findAllCustomerInvoice() {
        List<InvoiceCustomerResponse> list = new ArrayList<>();
        List<Customer_Invoice>customer_invoices = invoice_c_repository.findAll();
        for (Customer_Invoice customer_invoice: customer_invoices)
        {
            Payment_C payment_c = payment_c_repository.findById(customer_invoice.getPayment_c().getId_Payment()).get();
            Booking booking = bookingRepository.findBookingByIdPayment(payment_c.getId_Payment());
            list.add(new InvoiceCustomerResponse(customer_invoice.getId_C_Invoice(), payment_c.getId_Payment(), booking.getId_Booking(),
                    customer_invoice.getTotal_Of_Money(), customer_invoice.isStatus(),
                    payment_c.getType(), booking.getCustomer().getIdUser(), booking.getStartDate(), booking.getEndDate()));
        }
        return list;
    }

    @Override
    public List<InvoiceCustomerResponse> searchCustomerInvoiceByTypeOfPayment(String TypeOfPayment) {
        List<Payment_C>ListPayment = payment_c_repository.findPayment_C_By_TypeOfPayment(TypeOfPayment);
        List<InvoiceCustomerResponse> list = new ArrayList<>();
        for(Payment_C payment_c : ListPayment)
        {
            Customer_Invoice customer_invoice = invoice_c_repository.findCustomer_Invoice_By_Id_Payment(payment_c.getId_Payment());

            Booking booking = bookingRepository.findBookingByIdPayment(payment_c.getId_Payment());

            list.add(new InvoiceCustomerResponse(customer_invoice.getId_C_Invoice(), payment_c.getId_Payment(), booking.getId_Booking(),
                    customer_invoice.getTotal_Of_Money(), customer_invoice.isStatus(),
                    payment_c.getType(), booking.getCustomer().getIdUser(), booking.getStartDate(), booking.getEndDate()));

        }
        return list;
    }

    @Override
    public List<InvoiceResidentResponse> searchResidentInvoiceByTypeOfPayment(String TypeOfPayment) {
        List<Payment_R>ListPayment = payment_r_repository.findPaymentR_By_TypeOfPayment(TypeOfPayment);
        List<InvoiceResidentResponse>list = new ArrayList<>();
        for(Payment_R payment_r : ListPayment)
        {
            Resident_Slot resident_slot = resident_slot_repository.findResidentSlotByIdResident(payment_r.getResident().getIdUser());

            Resident_Invoice resident_invoice = invoice_r_repository.findResident_InvoiceByResidentPayment(payment_r.getId_Payment());

            list.add(new InvoiceResidentResponse(resident_invoice.getId_R_Invoice(), payment_r.getId_Payment(),
                    payment_r.getType(), resident_invoice.isStatus(), resident_invoice.getTotal_Of_Money(), resident_invoice.getTime(),
                    resident_slot.getResident().getIdUser()));
        }
        return list;
    }

    @Override
    public InvoiceResidentResponse searchResidentInvoiceId(String Id_R_Invoice) {
        Resident_Invoice resident_invoice = invoice_r_repository.findById(Id_R_Invoice).get();
        return new InvoiceResidentResponse(resident_invoice.getId_R_Invoice(), resident_invoice.getPayment_r().getId_Payment(),
                payment_r_repository.findById(resident_invoice.getPayment_r().getId_Payment()).get().getType(),
                resident_invoice.isStatus(), resident_invoice.getTotal_Of_Money(), resident_invoice.getTime(),
                payment_r_repository.findById(resident_invoice.getPayment_r().getId_Payment()).get().getResident().getIdUser());
    }

    @Override
    public InvoiceResidentResponse searchResidentInvoiceIdByResident(String id_Resident) {
        Resident_Slot resident_slot = resident_slot_repository.findResidentSlotByIdResident(id_Resident);

        Payment_R payment_r = payment_r_repository.findPayment_R_By_Resident_Slot(resident_slot.getId_R_Slot(), resident_slot.getArea().getId_Area());

        Resident_Invoice resident_invoice = invoice_r_repository.findResident_InvoiceByResidentPayment(payment_r.getId_Payment());
        return new InvoiceResidentResponse(resident_invoice.getId_R_Invoice(), payment_r.getId_Payment(),
                payment_r.getType(), resident_invoice.isStatus(), resident_invoice.getTotal_Of_Money(), resident_invoice.getTime(),
                resident_slot.getResident().getIdUser());
    }

    @Override
    public List<InvoiceResidentResponse> findAllResidentInvoice() {
        List<InvoiceResidentResponse> list = new ArrayList<>();
        List<Resident_Invoice>residentInvoices = invoice_r_repository.findAll();
        for (Resident_Invoice resident_invoice: residentInvoices)
        {
            list.add(new InvoiceResidentResponse(resident_invoice.getId_R_Invoice(), resident_invoice.getPayment_r().getId_Payment(),
                    payment_r_repository.findById(resident_invoice.getPayment_r().getId_Payment()).get().getType(),
                    resident_invoice.isStatus(), resident_invoice.getTotal_Of_Money(), resident_invoice.getTime(),
                    payment_r_repository.findById(resident_invoice.getPayment_r().getId_Payment()).get().getResident().getIdUser()));
        }
        return list;
    }

    @Override
    public User createNewResident(User dto) {
        userRepository.save(dto);
        residentRepository.save(new Resident(dto.getId(),  userRepository.findById(dto.getId()).get(), true));
        return dto;
    }

    @Override
    public User createNewCustomer(User dto) {
        userRepository.save(dto);
        customerRepository.save(new Customer(dto.getId(), true, userRepository.findById(dto.getId()).get()));
        return dto;
    }

    @Override
    public User updateCustomer_Resident(String idUser, UpdateDTO user) {
        User dto = userRepository.findById(idUser).get();
        dto.setId(idUser);
        dto.setEmail(user.getEmail());
        dto.setGender(user.isGender());
        dto.setDateofbirth(user.getDateofbirth());
        dto.setPassword(user.getPassword());
        dto.setFullname(user.getFullname());
        dto.setPhone(user.getPhone());
        userRepository.save(dto);
        return dto;
    }

    @Override
    public ResponseCustomerInfoSlot getCustomerInfoOfSlot(String id_Building, String id_C_slot) {
        Customer_Slot customerSlot = customer_slot_repository.findCustomerSlot(id_C_slot, id_Building);
        //System.out.println(customerSlot);

        Booking booking = bookingRepository.findIdBookingByCustomerSlot(id_C_slot, customerSlot.getArea().getId_Area());
//        System.out.println(booking);

        Payment_C payment_c = payment_c_repository.findPayment_C_By_Id_Booking(booking.getId_Booking());
        //System.out.println(payment_c);

        Customer_Invoice customer_invoice = invoice_c_repository.findCustomer_Invoice_By_Id_Payment(payment_c.getId_Payment());
        //System.out.println(customer_invoice);

        return new ResponseCustomerInfoSlot(booking.getCustomer().getIdUser(), booking.getStartDate(), booking.getEndDate(),
                booking.getStartTime(), booking.getEndTime(), payment_c.getId_Payment(), customer_invoice.getId_C_Invoice(),
                payment_c.getType(), customerSlot.getType_Of_Vehicle());
    }

    @Override
    public ResponseResidentInfoSlot getResidentInfoOfSlot(String id_Building, String id_R_slot) {
        Resident_Slot resident_slot = resident_slot_repository.findResidentSlot(id_R_slot, id_Building);

        Payment_R payment_r = payment_r_repository.findPayment_R_By_Resident_Slot(id_R_slot, resident_slot.getArea().getId_Area());

        Resident_Invoice resident_invoice = invoice_r_repository.findResident_InvoiceByResidentPayment(payment_r.getId_Payment());

        return new ResponseResidentInfoSlot(resident_slot.getResident().getIdUser(), resident_invoice.getTime(), payment_r.getId_Payment(),
                resident_invoice.getId_R_Invoice(), payment_r.getType(), resident_slot.getType_Of_Vehicle());

    }
}
