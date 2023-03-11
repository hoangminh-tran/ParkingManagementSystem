package com.demo.controller;

import com.demo.service.BuildingService;
import com.demo.utils.request.BuildingDTO;
import com.demo.utils.response.BuildingResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/building")
public class BuildingController {
    @Autowired
    BuildingService buildingService;

    @PostMapping("/save")
    public ResponseEntity<BuildingResponseDTO> save(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        BuildingDTO dto = mapper.readValue(json, BuildingDTO.class);
        return new ResponseEntity<>(buildingService.save(dto), HttpStatus.OK);
    }

    @GetMapping("/findById")
    public ResponseEntity<Optional<BuildingResponseDTO>> findById(@RequestParam("Id_Building") String Id_Building)
    {
        return new ResponseEntity<>(buildingService.findById(Id_Building), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<BuildingResponseDTO>> findAll()
    {
        return new ResponseEntity<>(buildingService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<BuildingResponseDTO> update(@RequestBody String json, @RequestParam("Id_Building") String Id_Building) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        BuildingDTO dto = mapper.readValue(json, BuildingDTO.class);
        return new ResponseEntity<>(buildingService.update(dto, Id_Building), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> update(@RequestParam("Id_Building") String Id_Building)
    {
        return new ResponseEntity<>(buildingService.delete(Id_Building), HttpStatus.OK);
    }
}