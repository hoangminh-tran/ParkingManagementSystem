package com.demo.controller;

import com.demo.service.CustomerService;
import com.demo.service.Customer_Slot_Service;
import com.demo.service.checkoutCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    checkoutCustomerService checkoutCustomerService;

    @GetMapping("/checkoutCustomer/{id_invoice}")
    ResponseEntity<String> checkoutCustomer(@PathVariable("id_invoice") String id_customer
            , @RequestParam String time) {
        return new ResponseEntity<>(checkoutCustomerService.checkoutCustomer(id_customer, time), HttpStatus.OK);
    }

}
