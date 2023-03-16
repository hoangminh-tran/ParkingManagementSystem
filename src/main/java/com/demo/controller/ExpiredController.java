package com.demo.controller;

import com.demo.entity.Customer_Invoice;
import com.demo.entity.Resident_Invoice;
import com.demo.service.CustomerExpiredService;
import com.demo.service.ResidentExpiredService;
import com.demo.utils.response.ExpiredResponse;
import com.demo.utils.response.InvoiceCustomerResponse;
import com.demo.utils.response.InvoiceResidentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/expired")
public class ExpiredController {
    @Autowired
    ResidentExpiredService residentExpiredService;

    @GetMapping("checkExpiredR/{id}")
    public ResponseEntity<List<ExpiredResponse>> getAllExpired(@PathVariable("id") String id) {
        return new ResponseEntity<>(residentExpiredService.checkExpired(id,
                residentExpiredService.findAllResidentInvoiceByResidentID(id)), HttpStatus.OK);
    }

    @GetMapping("/findAllInvoiceR/{id}")
    public ResponseEntity<List<InvoiceResidentResponse>> findAllInvoice(@PathVariable("id") String id){
        return new ResponseEntity<>(residentExpiredService.findAllResidentInvoiceByResidentID(id), HttpStatus.OK);
    }

    @Autowired
    CustomerExpiredService customerExpiredService;

    @GetMapping("checkExpiredC/{id}")
    public ResponseEntity<List<ExpiredResponse>> getAllExpiredC(@PathVariable("id") String id) {
        return new ResponseEntity<>(customerExpiredService.checkExpired(id,
                customerExpiredService.findAllCustomerInvoiceByCustomerID(id)), HttpStatus.OK);
    }

    @GetMapping("/findAllInvoiceC/{id}")
    public ResponseEntity<List<InvoiceCustomerResponse>> findAllInvoiceC(@PathVariable("id") String id){
        return new ResponseEntity<>(customerExpiredService.findAllCustomerInvoiceByCustomerID(id), HttpStatus.OK);
    }

}