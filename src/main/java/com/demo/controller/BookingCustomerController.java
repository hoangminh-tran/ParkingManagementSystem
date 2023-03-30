package com.demo.controller;

import com.demo.service.BookingCustomerService;
import com.demo.utils.request.BookingCustomerDTO;
import com.demo.utils.response.AreaResponseDTO;
import com.demo.utils.response.BookingCustomerResponseDTO;
import com.demo.utils.response.CancelBookingDTO;
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
    public ResponseEntity<BookingCustomerResponseDTO> save(@RequestBody String json, @RequestParam String time) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        BookingCustomerDTO dto = mapper.readValue(json, BookingCustomerDTO.class);
        return new ResponseEntity<>(bookingCustomerService.save(dto, time), HttpStatus.OK);
    }
    @GetMapping("/findBooking")
    public ResponseEntity<BookingCustomerResponseDTO> findBooking()
    {
        return new ResponseEntity<>(bookingCustomerService.findBooking(), HttpStatus.OK);
    }

    @PostMapping("/cancelBooking")
    public ResponseEntity<String> cancelBooking(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        CancelBookingDTO dto = mapper.readValue(json, CancelBookingDTO.class);
        return new ResponseEntity<>(bookingCustomerService.cancelBookingCustomer(dto), HttpStatus.OK);
    }

    @GetMapping("/messageCancelBookingCustomer")
    public ResponseEntity<String> messageCancelBookingCustomer()
    {
        return new ResponseEntity<>(bookingCustomerService.messageCancelBookingCustomer(), HttpStatus.OK);
    }

    @GetMapping("/messageBooking")
    public ResponseEntity<String> messageBooking()
    {
        return new ResponseEntity<>(bookingCustomerService.messageBooking(), HttpStatus.OK);
    }
}
