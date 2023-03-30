package com.demo.service.Impl;

import com.demo.entity.*;
import com.demo.repository.*;
import com.demo.service.PaymentCustomerService;
import com.demo.utils.request.CancelPaymentDTO;
import com.demo.utils.request.PaymentCustomerDTO;
import com.demo.utils.response.PaymentCustomerReponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static com.demo.entity.Money.*;

@Service
public class PaymentCustomerServiceImpl implements PaymentCustomerService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    Invoice_C_Repository invoice_c_repository;

    @Autowired
    Payment_C_Repository payment_c_repository;

    @Autowired
    Customer_Slot_Repository customer_slot_repository;

    @Autowired
    BuildingRepository buildingRepository;

    public PaymentCustomerReponseDTO paymentReponseDTO;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public PaymentCustomerReponseDTO save(PaymentCustomerDTO dto) {

        String Type_Of_Payment = dto.getType_Of_Payment();
        Long Id_Booking = dto.getId_Booking();

        Booking booking = bookingRepository.findById(Id_Booking).get();

        List<Customer_Invoice> invoiceList =  invoice_c_repository.findAll();
        List<Payment_C> paymentList =  payment_c_repository.findAll();

        Payment_C payment_c = new Payment_C("PC" + ((paymentList.size() == 0) ? 1 : paymentList.size() + 1 ), Type_Of_Payment, booking);
        payment_c_repository.save(payment_c);

        Customer_Slot customerSlot = customer_slot_repository.findCustomerSlotByIdBooking(dto.getId_Booking());
        Booking bookingInfo = bookingRepository.findById(dto.getId_Booking()).get();

        double total_of_money = calculateTotalOfMoney(customerSlot, bookingInfo);
        System.out.println(total_of_money);

        boolean Status_Invoice = Type_Of_Payment.equalsIgnoreCase("CASH") ? false : true;
        if(Status_Invoice == true)
        {
            Building building = buildingRepository.findById(dto.getId_Building()).get();
            building.setIncome(building.getIncome() + total_of_money);
            buildingRepository.save(building);
        }
        Customer_Invoice customer_invoice = new Customer_Invoice("IC" + ((invoiceList.size() == 0) ? 1 : invoiceList.size() + 1 ),
                total_of_money, Status_Invoice , payment_c);
        invoice_c_repository.save(customer_invoice);

        List<Customer_Invoice> invoiceList1 =  invoice_c_repository.findAll();
        List<Payment_C> paymentList1 =  payment_c_repository.findAll();

        paymentReponseDTO = new PaymentCustomerReponseDTO(Id_Booking, booking.getCustomer_slot().getId_C_Slot(), booking.getStartDate(),
                booking.getEndDate(), booking.getStartTime(), booking.getEndTime(), "PC" + paymentList1.size(),
                Type_Of_Payment, total_of_money,"IC" + invoiceList1.size(),  Status_Invoice);

        return paymentReponseDTO;
    }

    @Override
    public PaymentCustomerReponseDTO UpdateTypeOfPayment(PaymentCustomerReponseDTO dto) {
        String Type_Of_Payment = dto.getType_Of_Payment();
        Long Id_Booking = dto.getId_Booking();

        Booking booking = bookingRepository.findById(Id_Booking).get();

        Payment_C payment_c = payment_c_repository.findById(dto.getId_Payment()).get();
        payment_c.setType(dto.getType_Of_Payment());
        payment_c_repository.save(payment_c);

        Customer_Invoice customer_invoice = invoice_c_repository.findById(dto.getId_C_Invoice()).get();
        customer_invoice.setStatus(true);
        invoice_c_repository.save(customer_invoice);

         paymentReponseDTO = new PaymentCustomerReponseDTO(Id_Booking, booking.getCustomer_slot().getId_C_Slot(), booking.getStartDate(),
                booking.getEndDate(), booking.getStartTime(), booking.getEndTime(), payment_c.getId_Payment(),
                Type_Of_Payment, customer_invoice.getTotal_Of_Money(),
                customer_invoice.getId_C_Invoice(),  customer_invoice.isStatus());

        return paymentReponseDTO;
    }

    @Override
    public PaymentCustomerReponseDTO findPayment() {
        return paymentReponseDTO;
    }

    public static double calculateTotalOfMoney(Customer_Slot customerSlot, Booking bookingInfo)
    {
        //2022-06-20
        TimeZone vietnamTimeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        Calendar calendar = Calendar.getInstance(vietnamTimeZone);
        calendar.setTime(bookingInfo.getStartDate());
        int MM_st = calendar.get(Calendar.MONTH) + 1; // Note: Calendar.MONTH is zero-based, so add 1
        int DD_st = calendar.get(Calendar.DAY_OF_MONTH);  // Note: Calender.DAY_OF_MONTH is to get the Date of Month

        calendar.setTime(bookingInfo.getEndDate());
        int MM_en = calendar.get(Calendar.MONTH) + 1;
        int DD_en = calendar.get(Calendar.DAY_OF_MONTH);

        int hh_st = 0, hh_en = 0, mm_st = 0, mm_en = 0;
        if(bookingInfo.getStartTime().length() == 5)
        {
            hh_st = Integer.parseInt(bookingInfo.getStartTime().substring(0, 2));
            mm_st = Integer.parseInt(bookingInfo.getStartTime().substring(3, 5));
        }
        else if (bookingInfo.getStartTime().length() == 4)
        {
            hh_st = Integer.parseInt(bookingInfo.getStartTime().substring(0, 1));
            mm_st = Integer.parseInt(bookingInfo.getStartTime().substring(2, 4));
        }

        if(bookingInfo.getEndTime().length() == 5)
        {
            hh_en = Integer.parseInt(bookingInfo.getEndTime().substring(0, 2));
            mm_en = Integer.parseInt(bookingInfo.getEndTime().substring(3, 5));
        }
        else if (bookingInfo.getEndTime().length() == 4)
        {
            hh_en = Integer.parseInt(bookingInfo.getEndTime().substring(0, 1));
            mm_en = Integer.parseInt(bookingInfo.getEndTime().substring(2, 4));
        }

        if(hh_en == hh_st && mm_en == mm_st && MM_st == MM_en && DD_st == DD_en) return 0;
        if(MM_st < MM_en)
        {
            boolean check_startMonth_31Days = checkMonthHave31Days(MM_st);
            boolean check_startEnd_31Days = checkMonthHave31Days(MM_en);
            if (check_startMonth_31Days && !check_startEnd_31Days) // 03-29    04-01
            {
                if(DD_st > DD_en)
                {
                    DD_en += 31;
                }
            }
            else if (!check_startMonth_31Days && check_startEnd_31Days) // 04-29      05-01
            {
                if(DD_st > DD_en)
                {
                    DD_en += 30;
                }
            }
        }
        if(MM_st > MM_en || (MM_st == MM_en && DD_st > DD_en) || (MM_st == MM_en && DD_st == DD_en && hh_en < hh_st) ||
                (MM_st == MM_en && DD_st == DD_en && hh_en == hh_st && mm_en < mm_st))
        {
            return 0;
        }
        int day = 0;
        int hour = 0;
        if(DD_st <= DD_en) // 2022-06-15     2022-06-17
        {
            day += DD_en - DD_st;
            if(day == 0 && hh_st > hh_en) return 0;
            if (hh_st <= hh_en) {
                if (hh_en - hh_st >= 8 && mm_en - mm_st >= 0) // check condition: 12h00  21h:00
                {
                    day += 1;
                }
                else if (hh_en - hh_st == 8 && mm_en - mm_st < 0) // check condition: 12h50 20h:00
                {
                    hour += hh_en - hh_st - 1;
                }
                else if (hh_st - hh_en < 8 && mm_en - mm_st <= 0)// check condition: 12h30  17h:00
                {
                    hour += hh_en - hh_st;
                }
                else if (hh_en - hh_st < 8 && mm_en > mm_st) // check condition 12:00  12:30
                {
                    hour += 1;
                }
            }
            else if(day >= 1 && hh_st > hh_en) // Check startTime > endTime
            {
                day -= 1;
                if(hh_st >= 13 && hh_st <= 24)
                {
                    if(hh_en >= 13 && hh_en <= 24)
                    {
                        if(hh_st - hh_en >= 8 && mm_en - mm_st >= 0)  // check condition: 21h00  13h:00
                        {
                            day += 1;
                        }
                        else if(hh_st - hh_en == 8 && mm_en - mm_st < 0) // check condition: 20h30  12h:00
                        {
                            hour += hh_st - hh_en - 1;
                        }
                        else if(hh_st - hh_en < 8 && mm_en - mm_st >= 0) // check condition: 19h00  12h:00
                        {
                            hour += hh_st - hh_en;
                        }
                        else if(hh_st - hh_en < 8 && mm_en - mm_st < 0) // check condition: 19h00  12h:00
                        {
                            hour += hh_st - hh_en - 1;
                        }
                    }
                    else
                    {
                        if(hh_en >= 1 && hh_en <= 12)
                        {
                            if(hh_en + 24 - hh_st >= 8 && mm_en - mm_st >= 0)  // check condition 12h00   1h00
                            {
                                day += 1;
                            }
                            else if(hh_en + 24 - hh_st < 8 && mm_en - mm_st < 0) // check condition: 17h30 1h00
                            {
                                hour += hh_en + 24 - hh_st - 1;
                            }
                            else if(hh_en + 24 - hh_st < 8 && mm_en - mm_st >= 0) // check condition: 19h00  1h:00
                            {
                                hour += hh_en + 24 - hh_st;
                            }
                        }
                    }
                }
                else if(hh_st >= 1 && hh_st <= 12)
                {
                    if(hh_en >= 1 && hh_en <= 12)
                    {
                        if(hh_st - hh_en >= 8 && mm_en - mm_st >= 0)  // check condition: 21h00  13h:00
                        {
                            day += 1;
                        }
                        else if(hh_st - hh_en == 8 && mm_en - mm_st < 0) // check condition: 20h30  12h:00
                        {
                            hour += hh_st - hh_en - 1;
                        }
                        else if(hh_st - hh_en < 8 && mm_en - mm_st >= 0) // check condition: 19h00  12h:00
                        {
                            hour += hh_st - hh_en;
                        }
                        else if(hh_st - hh_en < 8 && mm_en - mm_st < 0) // check condition: 19h00  12h:00
                        {
                            hour += hh_st - hh_en - 1;
                        }
                    }
                }
            }
        }
//        System.out.println(day + " " + hour);
        double Total_Of_Money = 0;
        String type_of_vehicle = customerSlot.getType_Of_Vehicle();
        switch(type_of_vehicle)
        {
            case "Car":
                Total_Of_Money = CAR_MONEY_BY_HOUR * hour + CAR_MONEY_BY_DAY * day;
                break;
            case "Bike":
                Total_Of_Money = BIKE_MONEY_BY_HOUR * hour + BIKE_MONEY_BY_DAY * day;
                break;
            case "Motor":
                Total_Of_Money = MOTO_MONEY_BY_HOUR * hour + MOTO_MONEY_BY_DAY * day;
                break;
        }
        return Total_Of_Money;
        //return 0;
    }

    public static boolean checkMonthHave31Days(int mmEn) {
        boolean ok = false;
        switch (mmEn)
        {
            case 1:
                ok = true;
                break;
            case 3:
                ok = true;
                break;
            case 5:
                ok = true;
                 break;
            case 7:
                ok = true;
                break;
            case 8:
                ok = true;
                break;
            case 10:
                ok = true;
                break;
            case 12:
                ok = true;
                break;
        }
        return ok;
    }
}
