package com.demo.service;

import com.demo.utils.request.BookingAPI;
import com.demo.utils.request.BookingCustomerDTO;
import com.demo.utils.response.BookingCustomerResponseDTO;
import com.demo.utils.response.CancelBookingDTO;

import java.util.List;


public interface BookingCustomerService {
    BookingCustomerResponseDTO save(BookingCustomerDTO dto, String time);

    BookingCustomerResponseDTO findBooking();

    List<BookingAPI> findAllBooking();

    BookingAPI findBookingById(Long id);

    String cancelBookingCustomer(CancelBookingDTO dto);

    String messageCancelBookingCustomer();

    String messageBooking();
}
