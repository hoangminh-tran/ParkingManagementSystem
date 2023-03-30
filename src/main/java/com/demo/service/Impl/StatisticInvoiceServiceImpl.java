package com.demo.service.Impl;

import com.demo.entity.*;
import com.demo.repository.*;
import com.demo.service.CustomerExpiredService;
import com.demo.service.ResidentExpiredService;
import com.demo.service.StatisticInvoiceService;
import com.demo.utils.request.StatisticDTO;
import com.demo.utils.response.ExpiredResponse;
import com.demo.utils.response.FeeResponse;
import com.demo.utils.response.InvoiceCustomerResponse;
import com.demo.utils.response.InvoiceResidentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.demo.entity.Money.*;
import static com.demo.entity.Money.BIKE_MONEY_BY_DAY;
import static com.demo.service.Impl.PaymentCustomerServiceImpl.checkMonthHave31Days;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticInvoiceServiceImpl implements StatisticInvoiceService {
    private final CustomerExpiredService customerExpiredService;

    private  final ResidentExpiredService residentExpiredService;

    private final Invoice_C_Repository invoice_c_repository;

    private final Invoice_R_Repository invoice_r_repository;

    private final BookingRepository bookingRepository;

    private final Payment_C_Repository payment_c_repository;

    private final Payment_R_Repository payment_r_repository;

    private final Customer_Slot_Repository customer_slot_repository;

    private final Resident_Slot_Repository resident_slot_repository;
    @Override
    public List<StatisticDTO> ImportStatisticInvoice(String time) {
        List<StatisticDTO> list = new ArrayList<>();
        for(int i = 1; i <= 12; i++)
        {
            String month_st = "", month_en = "";
            //log.info("Count expire from Month " + i + ": " + countCustomerExpireInvoice(time, i) + " " + countResidentExpireInvoice(time, i));
            //log.info("Count expire from Month " + i + ": " + countCustomerInvoice(i) + " " + countResidentInvoice(i) +  " " + totalMoneyResidentInvoice(i) + totalMoneyResidentInvoice(i));
            if(i == 2)
            {
                month_st = "01-02-2023";
                month_en = "28-02-2023";
            }
            if(checkMonthHave31Days(i))
            {
                month_st = "01-" + i + "-2023";
                month_en = "31-" + i + "-2023";
            }
            else
            {
                month_st = "01-" + i + "-2023";
                month_en = "30-" + i + "-2023";
            }
            list.add(new StatisticDTO(month_st, month_en, totalMoneyCustomerInvoice(i) + totalMoneyResidentInvoice(i) + "",
                    countCustomerInvoice(i) + "", countResidentInvoice(i) + "", countCustomerExpireInvoice(time, i) +"", countResidentExpireInvoice(time, i) +""));
        }
        return list;
    }

    private int countCustomerInvoice(int month)
    {
        int count = 0;

        List<Booking> bookingList = bookingRepository.getAllBooking();
        Set<String> set = new HashSet<>();
        if (bookingList.size() > 0){
            for (Booking booking : bookingList)
            {
                TimeZone vnTimeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
                Calendar calendar = Calendar.getInstance(vnTimeZone);
                calendar.setTime(booking.getEndDate());
                int MM_en = calendar.get(Calendar.MONTH) + 1;

                Payment_C payment_c = payment_c_repository.findPayment_C_By_Id_Booking(booking.getId_Booking());
                if (payment_c != null)
                {
                    Customer_Invoice customer_invoice = invoice_c_repository.findCustomer_Invoice_By_Id_Payment(payment_c.getId_Payment());
                    if (!set.contains(booking.getCustomer().getIdUser()) && customer_invoice != null && customer_invoice.isStatus() && MM_en == month)
                    {
                        set.add(booking.getCustomer().getIdUser());
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private double totalMoneyCustomerInvoice(int month)
    {
        double count = 0;

        List<Booking> bookingList = bookingRepository.getAllBooking();
        Set<String> set = new HashSet<>();
        if (bookingList.size() > 0){
            for (Booking booking : bookingList)
            {
                TimeZone vnTimeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
                Calendar calendar = Calendar.getInstance(vnTimeZone);
                calendar.setTime(booking.getEndDate());
                int MM_en = calendar.get(Calendar.MONTH) + 1;

                Payment_C payment_c = payment_c_repository.findPayment_C_By_Id_Booking(booking.getId_Booking());
                if (payment_c != null)
                {
                    Customer_Invoice customer_invoice = invoice_c_repository.findCustomer_Invoice_By_Id_Payment(payment_c.getId_Payment());
                    if (customer_invoice != null && customer_invoice.isStatus() && MM_en == month)
                    {
                        count += customer_invoice.getTotal_Of_Money();
                    }
                }
            }
        }
        return count;
    }
    private double totalMoneyResidentInvoice(int month)
    {
        double count = 0;
        List<Payment_R> list_payment_R = payment_r_repository.findAll();
        if(list_payment_R.size() > 0){
            for(Payment_R payment_r : list_payment_R) {
                Resident_Invoice resident_invoice = invoice_r_repository.findResident_InvoiceByResidentPayment(payment_r.getId_Payment());

                if (resident_invoice != null)
                {
                    TimeZone vnTimeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
                    Calendar calendar = Calendar.getInstance(vnTimeZone);
                    calendar.setTime(resident_invoice.getTime());
                    int MM_en = calendar.get(Calendar.MONTH) + 1;
                    if(MM_en == month)
                    {
                        count += resident_invoice.getTotal_Of_Money();
                    }
                }
            }
        }
        return count;
    }

    private int countResidentInvoice(int month)
    {
        int count = 0;
        Set<String>set = new HashSet<>();
        List<Payment_R> list_payment_R = payment_r_repository.findAll();
        if(list_payment_R.size() > 0){
            for(Payment_R payment_r : list_payment_R) {
                Resident_Invoice resident_invoice = invoice_r_repository.findResident_InvoiceByResidentPayment(payment_r.getId_Payment());

                if (resident_invoice != null && !set.contains(payment_r.getResident().getIdUser()))
                {
                    TimeZone vnTimeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
                    Calendar calendar = Calendar.getInstance(vnTimeZone);
                    calendar.setTime(resident_invoice.getTime());
                    int MM_en = calendar.get(Calendar.MONTH) + 1;
                    if(MM_en == month)
                    {
                        set.add(payment_r.getResident().getIdUser());
                        count++;
                    }
                }
            }
        }
        return count;
    }
    private int countCustomerExpireInvoice(String time, int month) {
        int count = 0;
        List<Booking> bookingList = bookingRepository.getAllBooking();
        Set<String> set = new HashSet<>();
        if (bookingList.size() > 0){
            for (Booking booking : bookingList)
            {
                Payment_C payment_c = payment_c_repository.findPayment_C_By_Id_Booking(booking.getId_Booking());
                if (payment_c != null)
                {
                    Customer_Invoice customer_invoice = invoice_c_repository.findCustomer_Invoice_By_Id_Payment(payment_c.getId_Payment());
                    if (!set.contains(booking.getCustomer().getIdUser()) && customer_invoice != null)
                    {
                        set.add(booking.getCustomer().getIdUser());
                        count += checkExpiredCustomer(booking.getCustomer().getIdUser(),
                                customerExpiredService.findAllCustomerInvoiceByCustomerID(booking.getCustomer().getIdUser()),
                                time, month);
                    }
                }
            }
        }
        return count;
    }

    private int countResidentExpireInvoice(String time, int month)
    {
        int count = 0;
        Set<String>set = new HashSet<>();
        List<Payment_R> list_payment_R = payment_r_repository.findAll();
        if(list_payment_R.size() > 0){
            for(Payment_R payment_r : list_payment_R) {
                Resident_Invoice resident_invoice = invoice_r_repository.findResident_InvoiceByResidentPayment(payment_r.getId_Payment());
                if (resident_invoice != null && !set.contains(payment_r.getResident().getIdUser()))
                {
                    set.add(payment_r.getResident().getIdUser());
                    count += checkExpiredResident(payment_r.getResident().getIdUser(),
                            residentExpiredService.findAllResidentInvoiceByResidentID(payment_r.getResident().getIdUser()),
                            time, month);
                }
            }
        }
        return count;
    }

    public int checkExpiredCustomer(String id, List<InvoiceCustomerResponse> customerInvoices, String time, int month) {
        int count = 0;
        List<ExpiredResponse> expiredResponseList = null;
        for (InvoiceCustomerResponse ci : customerInvoices) {
            if (ci != null) {
                Payment_C pc = payment_c_repository.findPaymentByInvoiceId(ci.getId_C_Invoice());
                if (pc != null) {
                    String id_invoice = pc.getCustomer_invoice().getId_C_Invoice();
                    Booking bk = bookingRepository.findBookingByIdPayment(pc.getId_Payment());
                    if (bk != null) {
                        Date end_date = pc.getBooking().getEndDate();
                        Date current_date = new Date();

                        TimeZone vietnamTimeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
                        Calendar calendar = Calendar.getInstance(vietnamTimeZone);
                        calendar.setTime(current_date);
                        int current_month = calendar.get(Calendar.MONTH) + 1; // Note: Calendar.MONTH is zero-based, so add 1

                        int current_day = calendar.get(Calendar.DAY_OF_MONTH);
                        int current_hours = Integer.parseInt(time.substring(0, time.indexOf('a')));

                        calendar.setTime(end_date);
                        int end_month = calendar.get(Calendar.MONTH) + 1;
                        int end_day = calendar.get(Calendar.DAY_OF_MONTH);
                        String end_time = pc.getBooking().getEndTime();
                        int end_hours = Integer.parseInt(end_time.substring(0, 2));


                        int expired = 0;
                        double fine = 0;
                        double type_money = 1;
                        double type_moneyd = 1;

                        String type_vehicle = bk.getCustomer_slot().getType_Of_Vehicle();

                        if (type_vehicle.equalsIgnoreCase("Motor")) {
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
                        if (bk.is_checkout() == false) {
                            if (end_month == current_month) {
                                if (current_day == end_day) {
                                    if (current_hours >= end_hours) {
                                        expired = (current_day - end_day) * 24 + (current_hours - end_hours);
                                        fine = expired * type_money;
                                        warning = true;
                                    }
                                } else if (current_day > end_day) {
                                    expired = (current_day - end_day) * 24 + (current_hours - end_hours);
                                    fine = expired * type_money;
                                    warning = true;
                                }
                            }
                            if (warning == true) {
                                String current_time = Integer.parseInt(time.substring(0, time.indexOf('a'))) + ":" + current_date.getMinutes();
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
                                if (ex != null) {
                                    expiredResponseList.add(ex);
                                    Customer_Invoice invoice = invoice_c_repository.findCustomer_Invoice_By_Id_Payment(pc.getId_Payment());
                                    invoice.setStatus(false);
                                    invoice_c_repository.save(invoice);
                                    if(end_month == month) count++;
                                }

                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    public int checkExpiredResident(String id, List<InvoiceResidentResponse> resident_invoiceList, String time, int month) {
        int count = 0;
        List<ExpiredResponse> expiredResidentResponseList = null;
        if(resident_invoiceList == null){
            return 0;
        }
        for (InvoiceResidentResponse ri : resident_invoiceList) {
            Date end_date = ri.getTime();
            Date current_date = new Date();
            Payment_R pr = payment_r_repository.findPaymentByInvoiceId(ri.getId_R_Invoice());
            if(pr == null){
                return 0;
            }
            String id_invoice = pr.getResident_invoice().getId_R_Invoice();
            Resident_Slot rs = resident_slot_repository.findResidentSlotByIdResident(pr.getResident().getIdUser());
            if(rs == null){
                return 0;
            }

            TimeZone vietnamTimeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
            Calendar calendar = Calendar.getInstance(vietnamTimeZone);
            calendar.setTime(current_date);
            int current_month = calendar.get(Calendar.MONTH) + 1; // Note: Calendar.MONTH is zero-based, so add 1
            int current_day = calendar.get(Calendar.DAY_OF_MONTH);

            calendar.setTime(end_date);
            int end_month = calendar.get(Calendar.MONTH) + 1 + 1;
            int end_day = calendar.get(Calendar.DAY_OF_MONTH);


            int expired = 1;
            double fine = 0;
            boolean warning = false;
            double type_money = 1;
            if (rs.getType_Of_Vehicle().equals("Motor")) {
                type_money = MOTO_MONEY_BY_DAY * 1.5;
            } else if (rs.getType_Of_Vehicle().equals("Car")) {
                type_money = CAR_MONEY_BY_DAY * 1.5;
            } else if (rs.getType_Of_Vehicle().equals("Bike")) {
                type_money = BIKE_MONEY_BY_DAY * 1.5;
            }
            if (end_month == current_month) {
                expired = (current_day - end_day < 0) ? 0 : (current_day - end_day);
                fine = expired * type_money;
                if(expired > 0)
                    warning = true;
            } else if (end_month < current_month) {
                expired = (current_day - end_day) + (current_month - end_month) * 31;
                fine = expired * type_money;
                warning = true;
            }
            if (warning == true) {
                String current_time = Integer.parseInt(time.substring(0, time.indexOf('a'))) + ":" + current_date.getMinutes();
                String end_time = "00:00";
                ExpiredResponse ex = new ExpiredResponse(pr.getResident().getIdUser()
                        , id_invoice
                        , current_date
                        , current_time
                        , end_date
                        , end_time
                        , expired
                        , fine
                        , warning);
                if (expiredResidentResponseList == null) {
                    expiredResidentResponseList = new ArrayList<>();
                }
                if (ex != null) {
                    expiredResidentResponseList.add(ex);
                    Resident_Invoice invoice = invoice_r_repository.findResident_InvoiceByResidentPayment(pr.getId_Payment());
                    invoice.setStatus(false);
                    invoice_r_repository.save(invoice);
                    if(end_month == month) count++;
                }

            }
        }
        return count;
    }
}
