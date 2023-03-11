package com.demo.service;

import com.demo.utils.request.BookingAPI;
import com.demo.utils.request.BookingCustomerDTO;
import com.demo.utils.response.BookingCustomerResponseDTO;

import java.util.List;


public interface BookingCustomerService {
    BookingCustomerResponseDTO save(BookingCustomerDTO dto);

    BookingCustomerResponseDTO findBooking();

    List<BookingAPI> findAllBooking();

    BookingAPI findBookingById(Long id);
}
