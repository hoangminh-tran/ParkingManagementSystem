package com.demo.service.Impl;

import com.demo.entity.*;
import com.demo.repository.*;
import com.demo.service.BookingCustomerService;

import com.demo.service.CustomerExpiredService;
import com.demo.utils.request.BookingAPI;
import com.demo.utils.request.BookingCustomerDTO;
import com.demo.utils.request.BookingDTO;
import com.demo.utils.response.BookingCustomerResponseDTO;
import com.demo.utils.response.CancelBookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.demo.service.Impl.PaymentCustomerServiceImpl.calculateTotalOfMoney;

@Service
public class BookingCustomerServiceImpl implements BookingCustomerService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    Customer_Slot_Repository customer_slot_repository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    Payment_C_Repository payment_c_repository;

    @Autowired
    Invoice_C_Repository invoice_c_repository;


    public BookingCustomerResponseDTO bookingCustomerResponseDTO;

    public String message = "";

    @Autowired
    CustomerExpiredService cusExpired;

    @Override
    public BookingCustomerResponseDTO save(BookingCustomerDTO dto, String time) {

        TimeZone vietnamTimeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        Date current_date = new Date();
        Calendar calendar = Calendar.getInstance(vietnamTimeZone);

        //xet den ngay do va khoang thoi gian do
        calendar.setTime(current_date);
        int current_month = calendar.get(Calendar.MONTH) + 1; // Note: Calendar.MONTH is zero-based, so add 1
        int current_day = calendar.get(Calendar.DAY_OF_MONTH);
        int current_hours = Integer.parseInt(time.substring(0, time.indexOf('a')));

        calendar.setTime(dto.getStartDate());
        int start_month = calendar.get(Calendar.MONTH) + 1; // Note: Calendar.MONTH is zero-based, so add 1
        int start_day = calendar.get(Calendar.DAY_OF_MONTH);
        int start_hour = Integer.parseInt(dto.getStartTime().substring(0, dto.getStartTime().indexOf(':')));


        Customer customer = customerRepository.findById(dto.getIdUser()).get();
        if(customer.isStatus_Account() == true)
        {
            message = "Your Account has been banned you can not book";
            return null;
        }
        List<Booking> bookingList = bookingRepository.findBookingByCustomer(dto.getIdUser());
        if(bookingList.size() > 0)
        for(Booking booking : bookingList)
        {
            Payment_C payment_c = payment_c_repository.findPayment_C_By_Id_Booking(booking.getId_Booking());
            if(payment_c != null)
            {
                Customer_Invoice customer_invoice = invoice_c_repository.findCustomer_Invoice_By_Id_Payment(payment_c.getId_Payment());
                if(bookingList.size() >= 1 && cusExpired.checkExpired(customer.getIdUser(), cusExpired.findAllCustomerInvoiceByCustomerID(customer.getIdUser()), time) != null){
                    message = "You have an expired. Please payment before booking another slot";
                    return null;
                }
                if(customer_invoice.isStatus() == false)
                {
                    message = "You have to payment before booking another slot";
                    return null;
                }
            }
        }
        Customer_Slot customerSlot = customer_slot_repository.findCustomerSlot(dto.getId_C_Slot(), dto.getId_Building());
        if(customerSlot.isStatus_Slots() == true)
        {
            message = "The Slot is not empty. You cannot book that slot";
            return null;
        }

        customerSlot.setType_Of_Vehicle(dto.getType_Of_Vehicle());
        //--------------------------------------------------------------------------
//        if(checkSlotStartDate(current_month, current_day, current_hours, start_month, start_day, start_hour)) // check
//        {
//            customerSlot.setStatus_Slots(true); // set khi den ngay startDate biet currentDate de ma xet
//        }

        List<Booking> list = bookingRepository.findAll();

        Booking booking1 = new Booking(Long.parseLong(list.size() + 1 + ""),
                dto.getStartDate(), dto.getEndDate(), dto.getStartTime(), dto.getEndTime(),
                customerSlot, customerRepository.findById(dto.getIdUser()).get());
        System.out.println(booking1);
        double Total_of_Money = calculateTotalOfMoney(customerSlot, booking1);
        if(Total_of_Money == 0)
        {
            message = "Invalid StartDate EndDate StartTime EndTime";
            return null;
        }
        customer_slot_repository.save(customerSlot);
        bookingRepository.save(booking1);
        message = "Customer Booking Successfully";
        bookingCustomerResponseDTO =  new BookingCustomerResponseDTO(booking1.getId_Booking(), dto.getFullname(), dto.getEmail(), dto.getPhone(),
                dto.getId_Building(), dto.getType_Of_Vehicle(), dto.getId_C_Slot(), dto.getStartDate(),
                dto.getEndDate(), dto.getStartTime(), dto.getEndTime(), Total_of_Money);
        return  bookingCustomerResponseDTO;
    }

    private boolean checkSlotStartDate(int currMonth,int currDay, int currHour, int startMonth, int startDay, int startHour)
    {
        if(currMonth < startMonth) return false;
        if(currMonth == startMonth && currDay < startDay) return false;
        if(currMonth == startMonth && currDay == startDay && currHour < startHour) return false;
        return true;
    }

    @Override
    public String messageBooking() {
        return message;
    }

    @Override
    public BookingCustomerResponseDTO findBooking() {
        return bookingCustomerResponseDTO;
    }



    @Override
    public List<BookingAPI> findAllBooking() {
        List<Booking> bookingList = bookingRepository.findAll();
        List<BookingAPI>list = new ArrayList<>();
        for (Booking booking : bookingList)
        {
            list.add(new BookingAPI(booking.getId_Booking(), booking.getStartDate(), booking.getEndDate(), booking.getStartTime(),
                    booking.getEndTime(), booking.getCustomer().getIdUser(), customer_slot_repository.findById(booking.getCustomer_slot().getIndex()).get().getId_C_Slot()));
        }
        return list;
    }

    @Override
    public BookingAPI findBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).get();
        return new BookingAPI(booking.getId_Booking(), booking.getStartDate(), booking.getEndDate(), booking.getStartTime(),
                booking.getEndTime(), booking.getCustomer().getIdUser(), customer_slot_repository.findById(booking.getCustomer_slot().getIndex()).get().getId_C_Slot());
    }

    @Override
    public String cancelBookingCustomer(CancelBookingDTO dto) {
        //Update Status Slot
        Customer_Slot customerSlot = customer_slot_repository.findCustomerSlot(dto.getId_C_slot(), dto.getId_Building());
        customerSlot.setStatus_Slots(false);
        customer_slot_repository.save(customerSlot);

        //Update the Cancel Booking of Customer
        User user = userRepository.findById(dto.getId_Customer()).get();
        Customer customer = customerRepository.findById(dto.getId_Customer()).get();
        customer.setCancel_of_payments(customer.getCancel_of_payments() + 1);

        message = "Delete Successfully";

        if(customer.getCancel_of_payments() == 4) // Send notification if cancel booking == 4
        {
            message = "Cancel Booking 4 times";
        }
        else if(customer.getCancel_of_payments() + 1 >= 5) // Ban Account if cancel booking == 5
        {
            message = "Ban Customer";
            customer.setStatus_Account(true);
        }
        customerRepository.save(customer);

        //Delete the booking in the DB
        Booking booking = bookingRepository.findById(dto.getId_booking()).get();
        booking.set_deleted(true);
        booking.set_enabled(false);
        bookingRepository.save(booking);
        return message;
    }

    @Override
    public String messageCancelBookingCustomer() {
        return message;
    }
}
