package com.demo.controller;

import com.demo.service.*;
import com.demo.utils.request.*;
import com.demo.utils.response.AreaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/MoreFeatureGet")
public class MoreFeatureGet {
    @Autowired
    CustomerService customerService;

    @Autowired
    ResidentService residentService;

    @Autowired
    HeadManagerService headManagerService;
    @Autowired
    BuildingManagerService buildingManagerService;

    @Autowired
    AreaService areaService;

    @Autowired
    BuildingService buildingService;

    @Autowired
    BookingCustomerService bookingCustomerService;

    @Autowired
    Customer_Slot_Service customer_slot_service;

    @Autowired
    Resident_Slot_Service resident_slot_service;

    @GetMapping("/findByIdCustomer")
    public ResponseEntity<UserAPI> findByIdCustomer(@RequestParam("idCustomer") String idCustomer)
    {
        return new ResponseEntity<>(customerService.findByIdCustomer(idCustomer), HttpStatus.OK);
    }

    @GetMapping("/findCustomerAll")
    public ResponseEntity<List<UserAPI>> findCustomerAll()
    {
        return new ResponseEntity<>(customerService.findCustomerAll(), HttpStatus.OK);
    }

    @GetMapping("/findByIdResident")
    public ResponseEntity<UserAPI> findByIdResident(@RequestParam("idResident") String idResident)
    {
        return new ResponseEntity<>(residentService.findByIdResident(idResident), HttpStatus.OK);
    }

    @GetMapping("/findResidentAll")
    public ResponseEntity<List<UserAPI>> findResidentAll()
    {
        return new ResponseEntity<>(residentService.findResidentAll(), HttpStatus.OK);
    }

    @GetMapping("/findByIdSecurity")
    public ResponseEntity<SecurityDTO> findByIdSecurity(@RequestParam("id_Manager") String id_Manager)
    {
        return new ResponseEntity<>(buildingManagerService.findByIdSecurity(id_Manager), HttpStatus.OK);
    }

    @GetMapping("/findByIdBuildingManager")
    public ResponseEntity<SecurityDTO> findByIdBuildingManager(@RequestParam("IdBuildingManager") String IdBuildingManager)
    {
        return new ResponseEntity<>(headManagerService.findByIdBuildingManager(IdBuildingManager), HttpStatus.OK);
    }

    @GetMapping("/findAllArea")
    public ResponseEntity<List<AreaAPI>> findAllArea()
    {
        return new ResponseEntity<>(areaService.findAllArea(), HttpStatus.OK);
    }

    @GetMapping("/findAllBuilding")
    public ResponseEntity<List<BuildingAPI>> findAllBuilding()
    {
        return new ResponseEntity<>(buildingService.findAllBuilding(), HttpStatus.OK);
    }

    @GetMapping("/findAllBooking")
    public ResponseEntity<List<BookingAPI>> findAllBooking()
    {
        return new ResponseEntity<>(bookingCustomerService.findAllBooking(), HttpStatus.OK);
    }

    @GetMapping("/findBookingById")
    public ResponseEntity<BookingAPI> findBookingById(@RequestParam("IdBooking") Long IdBooking)
    {
        return new ResponseEntity<>(bookingCustomerService.findBookingById(IdBooking), HttpStatus.OK);
    }

    @GetMapping("/findAllCustomerSlot")
    public ResponseEntity<List<Customer_Slot_API>> findAllCustomerSlot()
    {
        return new ResponseEntity<>(customer_slot_service.listAllCustomerSlot(), HttpStatus.OK);
    }

    @GetMapping("/findByIdCustomerSlot")
    public ResponseEntity<Customer_Slot_API> findByIdCustomerSlot(@RequestParam("Id_Index") Long Id_Index)
    {
        return new ResponseEntity<>(customer_slot_service.findByIdCustomerSlot(Id_Index), HttpStatus.OK);
    }

    @GetMapping("/listAllResidentSlot")
    public ResponseEntity<List<Resident_Slot_API>> listAllResidentSlot()
    {
        return new ResponseEntity<>(resident_slot_service.listAllResidentSlot(), HttpStatus.OK);
    }

    @GetMapping("/findByIdResidentSlot")
    public ResponseEntity<Resident_Slot_API> findByIdResidentSlot(@RequestParam("Id_Index") Long Id_Index)
    {
        return new ResponseEntity<>(resident_slot_service.findByIdResidentSlot(Id_Index), HttpStatus.OK);
    }
}
