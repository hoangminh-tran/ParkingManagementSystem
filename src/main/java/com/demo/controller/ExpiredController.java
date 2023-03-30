package com.demo.controller;

import com.demo.entity.Customer_Invoice;
import com.demo.entity.Resident_Invoice;
import com.demo.service.CustomerExpiredService;
import com.demo.service.ResidentExpiredService;
import com.demo.utils.response.ExpiredResponse;
import com.demo.utils.response.FeeResponse;
import com.demo.utils.response.InvoiceCustomerResponse;
import com.demo.utils.response.InvoiceResidentResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expired")
public class ExpiredController {
    @Autowired
    ResidentExpiredService residentExpiredService;

    @GetMapping("/checkExpiredR/{id}")
    public ResponseEntity<List<ExpiredResponse>> getAllExpired(@PathVariable("id") String id, @RequestParam String time) {
        time = time.replace("\n", "");
        time = time.replace("\t", "");
        time = time.trim();
        System.out.println(time);
        return new ResponseEntity<>(residentExpiredService.checkExpired(id,
                residentExpiredService.findAllResidentInvoiceByResidentID(id), time), HttpStatus.OK);
    }

    @GetMapping("/getFeeCutomer/{id_invoice}")
    public ResponseEntity<FeeResponse> getCustomerFee(@PathVariable("id_invoice") String id_invoice,
                                                      @RequestParam String time
                                                      ){
//        time = time.replace("a", "");
        time = time.replace("\n", "");
        time = time.replace("\t", "");
        time = time.trim();
        System.out.println(time);   
        return new ResponseEntity<>(customerExpiredService.getCustomerFee(id_invoice, time), HttpStatus.CREATED);
    }

    @GetMapping("/findAllInvoiceR/{id}")
    public ResponseEntity<List<InvoiceResidentResponse>> findAllInvoice(@PathVariable("id") String id){
        return new ResponseEntity<>(residentExpiredService.findAllResidentInvoiceByResidentID(id), HttpStatus.OK);
    }

    @Autowired
    CustomerExpiredService customerExpiredService;

    @GetMapping("/checkExpiredC/{id}")
    public ResponseEntity<List<ExpiredResponse>> getAllExpiredC(@PathVariable("id") String id, @RequestParam String time) {
//        time = time.replace("a", "");
        time = time.replace("\n", "");
        time = time.replace("\t", "");
        time = time.trim();
        System.out.println(time);
        return new ResponseEntity<>(customerExpiredService.checkExpired(id,
                customerExpiredService.findAllCustomerInvoiceByCustomerID(id), time), HttpStatus.OK);
    }

    @GetMapping("/getFeeResident/{id_invoice}")
    public ResponseEntity<FeeResponse> getResidentFee(@PathVariable("id_invoice") String id_invoice,
                                                      @RequestParam String time){
        time = time.replace("\n", "");
        time = time.replace("\t", "");
        time = time.trim();
        System.out.println(time);
        return new ResponseEntity<>(residentExpiredService.getResidentFee(id_invoice, time), HttpStatus.CREATED);
    }

    @GetMapping("/findAllInvoiceC/{id}")
    public ResponseEntity<List<InvoiceCustomerResponse>> findAllInvoiceC(@PathVariable("id") String id){
        return new ResponseEntity<>(customerExpiredService.findAllCustomerInvoiceByCustomerID(id), HttpStatus.OK);
    }

    @GetMapping("/payC/{id_invoice}")
    public ResponseEntity<String> payFeeC(@PathVariable("id_invoice") String id_invoice, @RequestParam String time){
        time = time.replace("\n", "");
        time = time.replace("\t", "");
        time = time.trim();
        System.out.println(time);
        return new ResponseEntity<>(customerExpiredService.payFeeC(id_invoice, time), HttpStatus.OK);
    }
    @GetMapping("/payR/{id_invoice}")
    public ResponseEntity<String> payFeeR(@PathVariable("id_invoice") String id_invoice, @RequestParam String time){
        time = time.replace("\n", "");
        time = time.replace("\t", "");
        time = time.trim();
        System.out.println(time);
        return new ResponseEntity<>(residentExpiredService.payFeeR(id_invoice, time), HttpStatus.OK);
    }
}