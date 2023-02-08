package com.rest.api.controller;

import com.rest.api.service.ManageService;
import com.rest.api.utils.request.ManagerDTO;
import com.rest.api.utils.response.ManagerResponseDTO;
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
    public ResponseEntity<ManagerResponseDTO> save(@RequestBody ManagerDTO dto)
    {
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
    public ResponseEntity<ManagerResponseDTO> update(@RequestBody ManagerDTO dto, @RequestParam("IdUser") String IdUser)
    {
        return new ResponseEntity<>(manageService.update(dto, IdUser), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> update(@RequestParam("IdUser") String IdUser)
    {
        return new ResponseEntity<>(manageService.delete(IdUser), HttpStatus.OK);
    }
}
