package com.demo.controller;

import com.demo.entity.Money;
import com.demo.service.MoneyService;
import com.demo.utils.request.MoneyDTO;
import com.demo.utils.response.MoneyResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/updatemoney")
public class UpdateMoneyController {
    @Autowired
    MoneyService moneyService;

    @PostMapping("/save")
    public ResponseEntity<MoneyResponseDTO> save(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        MoneyDTO dto = mapper.readValue(json, MoneyDTO.class);
        return new ResponseEntity<>(moneyService.saveMoneyFromAPI(dto), HttpStatus.OK);
    }

    @GetMapping("/findAllMoney")
    public ResponseEntity<MoneyResponseDTO> findBooking()
    {
        return new ResponseEntity<>(moneyService.findALlTypeOfMoney(), HttpStatus.OK);
    }
}
