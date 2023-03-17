package com.demo.controller;

import com.demo.service.BookingCustomerService;
import com.demo.service.Resident_Slot_Service;
import com.demo.utils.request.BookingCustomerDTO;
import com.demo.utils.request.Resident_Slot_DTO;
import com.demo.utils.response.BookingCustomerResponseDTO;
import com.demo.utils.response.Resident_Slot_Response_DTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/residentslot")
public class ResidentSlotController {
    @Autowired
    Resident_Slot_Service resident_slot_service;

    @PostMapping("/saveResidentSlot")
    public ResponseEntity<Resident_Slot_Response_DTO> saveResidentSlot(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Resident_Slot_DTO dto = mapper.readValue(json, Resident_Slot_DTO.class);
        return new ResponseEntity<>(resident_slot_service.saveResidentSlot(dto), HttpStatus.OK);
    }

    @GetMapping("/findBooking")
    public ResponseEntity<Resident_Slot_Response_DTO> findBooking()
    {
        return new ResponseEntity<>(resident_slot_service.find_Resident_Slot(), HttpStatus.OK);
    }

    @GetMapping("/getMessageResidentBooking")
    public ResponseEntity<String> getMessageResidentBooking()
    {
        return new ResponseEntity<>(resident_slot_service.getMessageResidentBooking(), HttpStatus.OK);
    }

}
