package com.demo.controller;

import com.demo.service.AreaService;
import com.demo.utils.request.AreaDTO;
import com.demo.utils.response.AreaResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/area")
public class AreaController {
    @Autowired
    AreaService areaService;

    @PostMapping("/save")
    public ResponseEntity<AreaResponseDTO> save(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        AreaDTO dto = mapper.readValue(json, AreaDTO.class);
        return new ResponseEntity<>(areaService.save(dto), HttpStatus.OK);
    }

    @GetMapping("/findById")
    public ResponseEntity<Optional<AreaResponseDTO>> findById(@RequestParam("Id_Area") Long Id_Area)
    {
        return new ResponseEntity<>(areaService.findById(Id_Area), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<AreaResponseDTO>> findAll()
    {
        return new ResponseEntity<>(areaService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<AreaResponseDTO> update(@RequestBody String json, @RequestParam("Id_Area") Long Id_Area) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        AreaDTO dto = mapper.readValue(json, AreaDTO.class);
        return new ResponseEntity<>(areaService.update(dto, Id_Area), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> update(@RequestParam("Id_Area") Long Id_Area)
    {
        return new ResponseEntity<>(areaService.delete(Id_Area), HttpStatus.OK);
    }
}
