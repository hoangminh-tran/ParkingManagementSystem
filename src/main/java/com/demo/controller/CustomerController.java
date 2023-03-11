package com.demo.controller;

import com.demo.service.CustomerService;
import com.demo.utils.request.CustomerDTO;
import com.demo.utils.request.UserDTO;
import com.demo.utils.response.CustomerResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<CustomerResponseDTO> save(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CustomerDTO dto = mapper.readValue(json, CustomerDTO.class);
        return new ResponseEntity<>(customerService.save(dto), HttpStatus.OK);
    }

    @GetMapping("/findById")
    public ResponseEntity<Optional<CustomerResponseDTO>> findById(@RequestParam("IdUser") String IdUser)
    {
        return new ResponseEntity<>(customerService.findById(IdUser), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<CustomerResponseDTO>> findAll()
    {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerResponseDTO> update(@RequestBody String json, @RequestParam("IdUser") String IdUser) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CustomerDTO dto = mapper.readValue(json, CustomerDTO.class);
        return new ResponseEntity<>(customerService.update(dto, IdUser), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> update(@RequestParam("IdUser") String IdUser)
    {
        return new ResponseEntity<>(customerService.delete(IdUser), HttpStatus.OK);
    }
}
