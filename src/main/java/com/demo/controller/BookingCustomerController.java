package com.demo.controller;

import com.demo.service.BookingCustomerService;
import com.demo.utils.request.BookingCustomerDTO;
import com.demo.utils.response.AreaResponseDTO;
import com.demo.utils.response.BookingCustomerResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bookingCustomer")
public class BookingCustomerController {
   @Autowired
   BookingCustomerService bookingCustomerService;

    @PostMapping("/save")
    public ResponseEntity<BookingCustomerResponseDTO> save(@RequestBody String json) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        BookingCustomerDTO dto = mapper.readValue(json, BookingCustomerDTO.class);
        return new ResponseEntity<>(bookingCustomerService.save(dto), HttpStatus.OK);
    }

    @GetMapping("/findBooking")
    public ResponseEntity<BookingCustomerResponseDTO> findBooking()
    {
        return new ResponseEntity<>(bookingCustomerService.findBooking(), HttpStatus.OK);
    }
}
