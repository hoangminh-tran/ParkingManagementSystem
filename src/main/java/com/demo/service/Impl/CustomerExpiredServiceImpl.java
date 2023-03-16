package com.demo.service.Impl;

import com.demo.entity.*;
import com.demo.repository.*;
import com.demo.service.CustomerExpiredService;
import com.demo.service.PaymentCustomerService;
import com.demo.utils.response.ExpiredResponse;
import com.demo.utils.response.InvoiceCustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.demo.entity.Money.*;

@Service
public class CustomerExpiredServiceImpl implements CustomerExpiredService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    Payment_C_Repository paymentCRepository;

    @Autowired
    Invoice_C_Repository invoice_c_repository;

    @Autowired
    BookingRepository bookingRepository;

    @Override
    public List<InvoiceCustomerResponse> findAllCustomerInvoiceByCustomerID(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        List<InvoiceCustomerResponse> CIList = null;
        if (customer != null) {
            List<Booking> bk = bookingRepository.findBookingByCustomerID(id);
            if ((bk != null)) {
                for (Booking b : bk) {
                    Payment_C pc = paymentCRepository.findPayment_C_By_Id_Booking(b.getId_Booking());
                    if (pc != null) {
                        Customer_Invoice ci = invoice_c_repository.findCustomer_Invoice_By_Id_Payment(pc.getId_Payment());
                        InvoiceCustomerResponse icr = new InvoiceCustomerResponse(
                                ci.getId_C_Invoice(),
                                pc.getId_Payment(),
                                b.getId_Booking(),
                                ci.getTotal_Of_Money(),
                                ci.isStatus(),
                                pc.getType(),
                                id,
                                b.getStartDate(),
                                b.getEndDate());
                        if (CIList == null) {
                            CIList = new ArrayList<>();
                        }
                        if (ci != null)
                            CIList.add(icr);
                    }
                }
            }
        }
        return CIList;
    }

    @Override
    public List<ExpiredResponse> checkExpired(String id, List<InvoiceCustomerResponse> customerInvoices) {
        List<ExpiredResponse> expiredResponseList = null;
        for (InvoiceCustomerResponse ci : customerInvoices) {
            System.out.println(ci.getId_C_Invoice());
            Payment_C pc = paymentCRepository.findPaymentByInvoiceId(ci.getId_C_Invoice());
            String id_invoice = pc.getCustomer_invoice().getId_C_Invoice();
            Booking bk = bookingRepository.findBookingByIdPayment(pc.getId_Payment());
            Date end_date = pc.getBooking().getEndDate();
            Date current_date = new Date();

            TimeZone vietnamTimeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
            Calendar calendar = Calendar.getInstance(vietnamTimeZone);
            calendar.setTime(current_date);
            int current_month = calendar.get(Calendar.MONTH) + 1; // Note: Calendar.MONTH is zero-based, so add 1
            int current_day = calendar.get(Calendar.DAY_OF_MONTH);
            int current_hours = calendar.get(Calendar.HOUR_OF_DAY);

            calendar.setTime(end_date);
            int end_month = calendar.get(Calendar.MONTH) + 1;
            int end_day = calendar.get(Calendar.DAY_OF_MONTH);
            String end_time = pc.getBooking().getEndTime();
            int end_hours = Integer.parseInt(end_time.substring(0, 2));

            System.out.println(current_hours + ":" + current_day + ":" + current_month);
            System.out.println(end_hours + ":" + end_day + ":" + end_month);

            int expired = 0;
            double fine = 0;
            double type_money = 1;
            double type_moneyd = 1;

            String type_vehicle = bk.getCustomer_slot().getType_Of_Vehicle();

            if (type_vehicle.equals("Motor")) {
                type_money = MOTO_MONEY_BY_HOUR * 1.5;
                type_moneyd = MOTO_MONEY_BY_DAY * 1.5;
            } else if (type_vehicle.equals("Car")) {
                type_money = CAR_MONEY_BY_HOUR * 1.5;
                type_moneyd = CAR_MONEY_BY_DAY * 1.5;
            } else if (type_vehicle.equals("Bike")) {
                type_money = BIKE_MONEY_BY_HOUR * 1.5;
                type_moneyd = BIKE_MONEY_BY_DAY * 1.5;
            }
            boolean warning = false;
            if (end_month == current_month) {
                if (current_day >= end_day) {
                    if (current_hours >= end_hours) {
                        expired = (current_day - end_day) * 24 + (current_hours - end_hours);
                        fine = expired * type_money;
                        warning = true;
                    }
                }
            }
//            else if (end_month < current_month) {
//                expired = Math.abs(current_day - end_day) + (current_month - end_month) * 31;
//                fine = expired * 10;
//                warning = true;
//            }
            if (warning == true) {
                String current_time = current_date.getHours() + ":" + current_date.getMinutes();
                ExpiredResponse ex = new ExpiredResponse(id
                        , id_invoice
                        , current_date
                        , current_time
                        , end_date
                        , end_time
                        , expired
                        , fine
                        , warning);
                if (expiredResponseList == null) {
                    expiredResponseList = new ArrayList<>();
                }
                if(ex != null)
                    expiredResponseList.add(ex);
            }
        }
        return expiredResponseList;
//        return null;
    }
}