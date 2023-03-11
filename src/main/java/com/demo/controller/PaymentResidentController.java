package com.demo.controller;

import com.demo.service.PaymentCustomerService;
import com.demo.service.PaymentResidentService;
import com.demo.utils.request.PaymentCustomerDTO;
import com.demo.utils.request.PaymentResidentDTO;
import com.demo.utils.response.PaymentCustomerReponseDTO;
import com.demo.utils.response.PaymentResidentResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paymentResident")
public class PaymentResidentController {
    @Autowired
    PaymentResidentService paymentResidentService;

    @PostMapping("/save")
    public ResponseEntity<PaymentResidentResponseDTO> save(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PaymentResidentDTO dto = mapper.readValue(json, PaymentResidentDTO.class);
        return new ResponseEntity<>(paymentResidentService.save(dto), HttpStatus.OK);
    }

    @GetMapping("/findPayment")
    public ResponseEntity<PaymentResidentResponseDTO> findPayment()
    {
        return new ResponseEntity<>(paymentResidentService.findPayment(), HttpStatus.OK);
    }
}
