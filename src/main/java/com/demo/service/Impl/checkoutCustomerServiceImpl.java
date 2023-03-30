package com.demo.service.Impl;

import com.demo.entity.*;
import com.demo.repository.*;
import com.demo.service.CustomerService;
import com.demo.service.checkoutCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class checkoutCustomerServiceImpl implements checkoutCustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    Invoice_C_Repository invoice_c_repository;

    @Autowired
    Payment_C_Repository payment_c_repository;

    @Autowired
    CustomerExpiredServiceImpl customerExpired;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    Customer_Slot_Repository customer_slot_repository;

    @Override
    public String checkoutCustomer(String id_invoice, String time) {
        Payment_C pc = payment_c_repository.findPaymentByInvoiceId(id_invoice);
        if (pc != null) {
            Customer_Invoice ci = invoice_c_repository.findCustomer_Invoice_By_Id_Payment(pc.getId_Payment());
            if (ci == null) {
                return "Can not find invoice with id: " + id_invoice;
            }
            if (ci.isStatus() == false || customerExpired.getCustomerFee(id_invoice, time) != null) {
                return "Please pay fee before checkout!";
            }
            Booking bk = pc.getBooking();
            Customer_Slot cs = customer_slot_repository.findCustomerSlotByIdBooking(bk.getId_Booking());
            cs.setStatus_Slots(false);
            customer_slot_repository.save(cs);
            bk.set_checkout(true);
            bk.set_enabled(true);
            bookingRepository.save(bk);
        }
        return "checkout successfully!";
    }
}
