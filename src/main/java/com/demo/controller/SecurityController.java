package com.demo.controller;

import com.demo.entity.User;
import com.demo.repository.Customer_Slot_Repository;
import com.demo.service.Customer_Slot_Service;
import com.demo.service.SecurityService;
import com.demo.service.UserService;
import com.demo.utils.request.UpdateDTO;
import com.demo.utils.request.UserAPI;
import com.demo.utils.request.UserDTO;
import com.demo.utils.response.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<User> createCustomer(@RequestBody String json) throws Exception {
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
    public ResponseEntity<List<User>>getAllResidentFromBuilding(@PathVariable("Id_Building") String Id_Building)
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
    public ResponseEntity<User> createResident(@RequestBody String json) throws Exception {
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

}
