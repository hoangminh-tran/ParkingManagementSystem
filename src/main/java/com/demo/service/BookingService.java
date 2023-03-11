package com.demo.service;

import com.demo.utils.request.BookingDTO;
import com.demo.utils.response.BookingRepsonseDTO;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    BookingRepsonseDTO save(BookingDTO dto) throws Exception;

    Optional<BookingRepsonseDTO> findById(Long Id_Booking);

    List<BookingRepsonseDTO> findAll();

    BookingRepsonseDTO update(BookingDTO dto, Long Id_Booking) throws Exception ;

    String delete (Long Id_Booking);
}
