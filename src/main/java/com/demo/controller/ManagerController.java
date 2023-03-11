package com.demo.controller;

import com.demo.service.ManageService;
import com.demo.utils.request.ManagerDTO;
import com.demo.utils.request.UserDTO;
import com.demo.utils.response.ManagerResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    ManageService manageService;

    @PostMapping("/save")
    public ResponseEntity<ManagerResponseDTO> save(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ManagerDTO dto = mapper.readValue(json, ManagerDTO.class);
        return new ResponseEntity<>(manageService.save(dto), HttpStatus.OK);
    }

    @GetMapping("/findById")
    public ResponseEntity<Optional<ManagerResponseDTO>> findById(@RequestParam("IdUser") String IdUser)
    {
        return new ResponseEntity<>(manageService.findById(IdUser), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ManagerResponseDTO>> findAll()
    {
        return new ResponseEntity<>(manageService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ManagerResponseDTO> update(@RequestBody String json, @RequestParam("IdUser") String IdUser) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ManagerDTO dto = mapper.readValue(json, ManagerDTO.class);
        return new ResponseEntity<>(manageService.update(dto, IdUser), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> update(@RequestParam("IdUser") String IdUser)
    {
        return new ResponseEntity<>(manageService.delete(IdUser), HttpStatus.OK);
    }
}
