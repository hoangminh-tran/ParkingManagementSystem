package com.demo.service.Impl;

import com.demo.entity.*;
import com.demo.repository.*;
import com.demo.service.PaymentCustomerService;
import com.demo.utils.request.CancelPaymentDTO;
import com.demo.utils.request.PaymentCustomerDTO;
import com.demo.utils.response.PaymentCustomerReponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public String CancelPayment(CancelPaymentDTO dto) {
        Booking booking = bookingRepository.findById(dto.getId_Booking()).get();
        Customer customer = customerRepository.findById(booking.getCustomer().getIdUser()).get();
        customer.setCancel_of_payments(customer.getCancel_of_payments() + 1);
        customerRepository.save(customer);

        invoice_c_repository.deleteById(dto.getId_C_Invoice());

        payment_c_repository.deleteById(dto.getId_Payment());

        bookingRepository.deleteById(dto.getId_Booking());
        return "Cancel Payment Successfully";
    }

    @Override
    public PaymentCustomerReponseDTO findPayment() {
        return paymentReponseDTO;
    }

    private double calculateTotalOfMoney(Customer_Slot customerSlot, Booking bookingInfo)
    {
        //2022-06-20
        int DD_st = Integer.parseInt((bookingInfo.getStartDate() + "").substring(8, 10));
        int DD_en = Integer.parseInt((bookingInfo.getEndDate() + "").substring(8, 10));

        int hh_st = Integer.parseInt(bookingInfo.getStartTime().substring(0, 2));
        int hh_en = Integer.parseInt(bookingInfo.getEndTime().substring(0, 2));

        int mm_st = Integer.parseInt(bookingInfo.getStartTime().substring(3, 5));
        int mm_en = Integer.parseInt(bookingInfo.getEndTime().substring(3, 5));

        int day = 0;
        int hour = 0;
        if(DD_st <= DD_en) // 2022-06-15     2022-06-17
        {
            day += DD_en - DD_st;
            if (hh_st <= hh_en) {
                if (hh_en - hh_st >= 8 && mm_en - mm_st >= 0) // check condition: 12h00  21h:00
                {
                    day += 1;
                }
                else if (hh_en - hh_st == 8 && mm_en - mm_st < 0) // check condition: 12h50 20h:00
                {
                    hour += hh_en - hh_st - 1;
                }
                else if (hh_en - hh_st > 0)// check condition: 12h30  17h:00
                {
                    hour += hh_en - hh_st;
                }
                else if (hh_en - hh_st == 0 && mm_en > mm_st) // check condition 12:00  12:30
                {
                    hour += 1;
                }
            }
        }

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
    }
}
