package com.demo.controller;

import com.demo.entity.Customer_Invoice;
import com.demo.entity.User;
import com.demo.repository.Customer_Slot_Repository;
import com.demo.repository.Invoice_C_Repository;
import com.demo.repository.UserRepository;
import com.demo.service.Customer_Slot_Service;
import com.demo.service.SecurityService;
import com.demo.service.UserService;
import com.demo.utils.request.*;
import com.demo.utils.response.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/security")
public class SecurityController {
    @Autowired
    SecurityService securityService;
    @Autowired
    Customer_Slot_Service customer_slot_service;

    @Autowired
    Customer_Slot_Repository customer_slot_repository;

    @PostMapping("/createCustomer")
    public ResponseEntity<String> createCustomer(@RequestBody String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        User dto = mapper.readValue(json, User.class);
        return new ResponseEntity<>(securityService.createNewCustomer(dto), HttpStatus.OK);
    }

    @GetMapping("/ListAllCustomerFromBuilding/{Id_Building}")
    public ResponseEntity<List<UserAPI>> getAllCustomerFromBuilding(@PathVariable("Id_Building") String Id_Building)
    {
        return new ResponseEntity<>(securityService.getAllCustomerFromBuilding(Id_Building), HttpStatus.OK);
    }

    @GetMapping("/ListAllResidentFromBuilding/{Id_Building}")
    public ResponseEntity<List<ResidentAPI>>getAllResidentFromBuilding(@PathVariable("Id_Building") String Id_Building)
    {
        return new ResponseEntity<>(securityService.getAllResidentFromBuilding(Id_Building) , HttpStatus.OK);
    }

    @GetMapping("/searchCustomerInvoiceId/{Id_C_Invoice}")
    public ResponseEntity<InvoiceCustomerResponse>searchCustomerInvoiceId(@PathVariable("Id_C_Invoice") String Id_C_Invoice)
    {
        return new ResponseEntity<>(securityService.searchCustomerInvoiceId(Id_C_Invoice) , HttpStatus.OK);
    }

    @GetMapping("/searchCustomerInvoiceByCustomer/{Id_Customer}")
    public ResponseEntity<List<InvoiceCustomerResponse>>searchCustomerInvoiceByCustomer(@PathVariable("Id_Customer") String Id_Customer)
    {
        return new ResponseEntity<>(securityService.searchCustomerInvoiceByCustomer(Id_Customer) , HttpStatus.OK);
    }

    @GetMapping("/searchCustomerInvoiceByTypeOfPayment/{typeOfPayment}")
    public ResponseEntity<List<InvoiceCustomerResponse>>searchCustomerInvoiceByTypeOfPayment(@PathVariable("typeOfPayment") String typeOfPayment)
    {
        return new ResponseEntity<>(securityService.searchCustomerInvoiceByTypeOfPayment(typeOfPayment) , HttpStatus.OK);
    }

    @GetMapping("/searchResidentInvoiceByTypeOfPayment/{typeOfPayment}")
    public ResponseEntity<List<InvoiceResidentResponse>>searchResidentInvoiceByTypeOfPayment(@PathVariable("typeOfPayment") String typeOfPayment)
    {
        return new ResponseEntity<>(securityService.searchResidentInvoiceByTypeOfPayment(typeOfPayment) , HttpStatus.OK);
    }
    @GetMapping("/findAllCustomerInvoice")
    public ResponseEntity<List<InvoiceCustomerResponse>>findAllCustomerInvoice(){
        return new ResponseEntity<>(securityService.findAllCustomerInvoice() , HttpStatus.OK);
    }

    @GetMapping("/searchResidentInvoiceId/{Id_R_Invoice}")
    public ResponseEntity<InvoiceResidentResponse>searchResidentInvoiceId(@PathVariable("Id_R_Invoice") String Id_R_Invoice)
    {
        return new ResponseEntity<>(securityService.searchResidentInvoiceId(Id_R_Invoice) , HttpStatus.OK);
    }

    @GetMapping("/searchResidentInvoiceIdByResident/{id_Resident}")
    public ResponseEntity<InvoiceResidentResponse>searchResidentInvoiceIdByResident(@PathVariable("id_Resident") String id_Resident)
    {
        return new ResponseEntity<>(securityService.searchResidentInvoiceIdByResident(id_Resident) , HttpStatus.OK);
    }

    @GetMapping("/findAllResidentInvoice")
    public ResponseEntity<List<InvoiceResidentResponse>>findAllResidentInvoice(){
        return new ResponseEntity<>(securityService.findAllResidentInvoice(), HttpStatus.OK);
    }

    @GetMapping("/findAllSlot/{Id_Building}")
    public ResponseEntity<List<PresentSlotOfBuilding>> findAllSlot(@PathVariable("Id_Building") String Id_Building)
    {
        return new ResponseEntity<>(customer_slot_service.findAllSlot(Id_Building), HttpStatus.OK);
    }

    @PostMapping("/createResident")
    public ResponseEntity<String> createResident(@RequestBody String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        User dto = mapper.readValue(json, User.class);
        return new ResponseEntity<>(securityService.createNewResident(dto), HttpStatus.OK);
    }

    @PutMapping("/updateCustomer_Resident")
    public ResponseEntity<User> updateCustomer_Resident(@RequestBody String json, @RequestParam("idUser") String idUser) throws  Exception {
        ObjectMapper mapper = new ObjectMapper();
        UpdateDTO dto = mapper.readValue(json, UpdateDTO.class);
        return new ResponseEntity<>(securityService.updateCustomer_Resident(idUser, dto), HttpStatus.OK);
    }

    @GetMapping("/ResponseCustomerInfoSlot")
    public ResponseEntity<ResponseCustomerInfoSlot> ResponseCustomerInfoSlot(@RequestParam("id_Building") String id_Building,
                                                                             @RequestParam("id_C_Slot") String id_C_Slot)
    {
        return new ResponseEntity<>(securityService.getCustomerInfoOfSlot(id_Building, id_C_Slot), HttpStatus.OK);
    }

    @GetMapping("/ResponseResidentInfoSlot")
    public ResponseEntity<ResponseResidentInfoSlot> ResponseResidentInfoSlot(@RequestParam("id_Building") String id_Building,
                                                                             @RequestParam("id_R_Slot") String id_R_Slot)
    {
        return new ResponseEntity<>(securityService.getResidentInfoOfSlot(id_Building, id_R_Slot), HttpStatus.OK);
    }

    @GetMapping("/searchCustomerByEmail/{email}")
    public ResponseEntity<List<UserAPI>> searchCustomerByEmail(@PathVariable("email") String email)
    {
        return new ResponseEntity<>(securityService.searchCustomerByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/searchResidentByEmail/{email}")
    public ResponseEntity<List<UserAPI>> searchResidentByEmail(@PathVariable("email") String email)
    {
        return new ResponseEntity<>(securityService.searchResidentByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/searchCustomerByPhone/{phone}")
    public ResponseEntity<List<UserAPI>> searchCustomerByPhone(@PathVariable("phone") String phone)
    {
        return new ResponseEntity<>(securityService.searchCustomerByPhone(phone), HttpStatus.OK);
    }

    @GetMapping("/searchResidentByPhone/{phone}")
    public ResponseEntity<List<UserAPI>> searchResidentByPhone(@PathVariable("phone") String phone)
    {
        return new ResponseEntity<>(securityService.searchResidentByPhone(phone), HttpStatus.OK);
    }

    @PutMapping("/BanOrUnBanCustomer/{id_Customer}")
    public ResponseEntity<UserAPI> BanOrUnBanCustomer(@PathVariable("id_Customer") String id_Customer)
    {
        return new ResponseEntity<>(securityService.BanOrUnBanCustomer(id_Customer), HttpStatus.OK);
    }

    @PutMapping("/BanOrUnBanResident/{id_Resident}")
    public ResponseEntity<UserAPI> BanOrUnBanResident(@PathVariable("id_Resident") String id_Resident)
    {
        return new ResponseEntity<>(securityService.BanOrUnBanResident(id_Resident), HttpStatus.OK);
    }

    @PutMapping("/changeStatusInvoiceCustomer/{id_C_invoice}")
    public ResponseEntity<String> changeStatusInvoiceCustomer(@PathVariable("id_C_invoice") String id_C_invoice)
    {
        return new ResponseEntity<>(securityService.changeStatusInvoiceCustomer(id_C_invoice), HttpStatus.OK);
    }

    @PutMapping("/changeStatusInvoiceResident/{id_R_invoice}")
    public ResponseEntity<String> changeStatusInvoiceResident(@PathVariable("id_R_invoice") String id_R_invoice)
    {
        return new ResponseEntity<>(securityService.changeStatusInvoiceResident(id_R_invoice), HttpStatus.OK);
    }

    @GetMapping("/getCustomerBookingHistory/{id_Customer}")
    public ResponseEntity<List<CustomerBookingHistory>> getCustomerBookingHistory(@PathVariable("id_Customer") String id_Customer)
    {
        return new ResponseEntity<>(securityService.getCustomerBookingHistory(id_Customer), HttpStatus.OK);
    }

    @GetMapping("/getResidentBookingHistory/{id_Resident}")
    public ResponseEntity<List<ResidentBookingHistory>> getResidentBookingHistory(@PathVariable("id_Resident") String id_Resident)
    {
        return new ResponseEntity<>(securityService.getResidentBookingHistory(id_Resident), HttpStatus.OK);
    }

    @Autowired
    UserRepository userRepository;

    @GetMapping("/searchUser")
    public ResponseEntity<List<User>> search(@RequestParam("typeOfSearch") String typeOfSearch, @RequestParam("value") String value){
        if(typeOfSearch.toUpperCase().equals("PHONE")){
            return new ResponseEntity<>(userRepository.findAllByPhone(value), HttpStatus.OK);
        }
        if(typeOfSearch.toUpperCase().equals("EMAIL")){
            return new ResponseEntity<>(userRepository.findAllByEmail(value), HttpStatus.OK);
        }
        if(typeOfSearch.toUpperCase().equals("IDUSER")){
            List<User> list = new ArrayList<>();
            list.add(userRepository.findById(value).get());
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return null;
    }

//    @Autowired
//    Invoice_C_Repository invoiceCRepository;
//
//    @GetMapping("/searchInvoice")
//    public ResponseEntity<List<T>> search(@RequestParam("typeOfSearch") String typeOfSearch, @RequestParam("value") String value){
//        if(typeOfSearch.toUpperCase().equals("IDINVOICE")){
//            List<Customer_Invoice> list = invoiceCRepository.findAllById_C_Invoice(value);
//            Customer_Invoice ci = list.get(0);
//            return new ResponseEntity<>(, HttpStatus.OK);
//        }
//        if(typeOfSearch.toUpperCase().equals("EMAIL")){
//            return new ResponseEntity<>(userRepository.findAllByEmail(value), HttpStatus.OK);
//        }
//        if(typeOfSearch.toUpperCase().equals("IDUSER")){
//            List<User> list = new ArrayList<>();
//            list.add(userRepository.findById(value).get());
//            return new ResponseEntity<>(list, HttpStatus.OK);
//        }
//        return null;
//    }
}

