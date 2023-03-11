package com.demo.controller;

import com.demo.service.ResidentService;
import com.demo.utils.request.ResidentDTO;
import com.demo.utils.request.UserDTO;
import com.demo.utils.response.ResidentResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/resident")
public class ResidentController {
    @Autowired
    ResidentService residentService;


    @PostMapping("/save")
    public ResponseEntity<ResidentResponseDTO> save(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ResidentDTO dto = mapper.readValue(json, ResidentDTO.class);
        return new ResponseEntity<>(residentService.save(dto), HttpStatus.OK);
    }

    @GetMapping("/findById")
    public ResponseEntity<Optional<ResidentResponseDTO>> findById(@RequestParam("IdUser") String IdUser)
    {
        return new ResponseEntity<>(residentService.findById(IdUser), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ResidentResponseDTO>> findAll()
    {
        return new ResponseEntity<>(residentService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ResidentResponseDTO> update(@RequestBody String json, @RequestParam("IdUser") String IdUser) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ResidentDTO dto = mapper.readValue(json, ResidentDTO.class);
        return new ResponseEntity<>(residentService.update(dto, IdUser), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> update(@RequestParam("IdUser") String IdUser)
    {
        return new ResponseEntity<>(residentService.delete(IdUser), HttpStatus.OK);
    }
}
