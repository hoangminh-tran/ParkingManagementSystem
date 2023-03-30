package com.demo.controller;

import com.demo.service.Customer_Slot_Service;
import com.demo.service.PresentSlotOfEachBuilding;
import com.demo.utils.request.CustomerDTO;
import com.demo.utils.request.DateDTO;
import com.demo.utils.response.Customer_Slot_Response_DTO;
import com.demo.utils.response.PresentSlotResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hpsf.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/present_slot")
public class PresentSlotController {
    @Autowired
    PresentSlotOfEachBuilding presentSlotOfEachBuilding;

    @PostMapping("/findAll/{Id_Building}")
    public ResponseEntity<List<PresentSlotResponseDto>>present_slot(@PathVariable("Id_Building") String Id_Building, @RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        DateDTO dto = mapper.readValue(json, DateDTO.class);
        return new ResponseEntity<>(presentSlotOfEachBuilding.findAll(Id_Building, dto) , HttpStatus.OK);
    }
}
