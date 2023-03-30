package com.demo.controller;

import com.demo.entity.User;
import com.demo.service.HeadManagerService;
import com.demo.service.StatisticInvoiceService;
import com.demo.utils.request.*;
import com.demo.utils.response.BuildingManagerResponse;
import com.demo.utils.response.UserResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/headManager")
@RequiredArgsConstructor
public class HeadManageController {
    @Autowired
    HeadManagerService headManagerService;

    private final StatisticInvoiceService statisticInvoiceService;

    @GetMapping("/findAllBuildingManager")
    public ResponseEntity<List<SecurityDTO>> findAllSecurity(){
        return new ResponseEntity<>(headManagerService.findAllBuildingManager(), HttpStatus.OK);
    }

    @GetMapping("/listAllManager")
    public ResponseEntity<List<SecurityDTO>> listAllManager(){
        return new ResponseEntity<>(headManagerService.listAllManager(), HttpStatus.OK);
    }

    @PutMapping("/BanOrUnbanBuildingManager")
    public ResponseEntity<SecurityDTO>BanOrUnbanBuildingManager(@RequestParam("idUser") String idUser,
                                                                @RequestParam("status") boolean status){
        return new ResponseEntity<>(headManagerService.BanOrUnbanBuildingManager(idUser, status), HttpStatus.OK);
    }

    @PutMapping("/updateBuildingManager")
    public ResponseEntity<SecurityDTO> updateBuildingManager(@RequestBody String json, @RequestParam("idUser") String idUser) throws  Exception {
        ObjectMapper mapper = new ObjectMapper();
        UpdateDTO dto = mapper.readValue(json, UpdateDTO.class);
        return new ResponseEntity<>(headManagerService.updateBuildingManager(idUser, dto), HttpStatus.OK);
    }

    @PostMapping("/createBuildingManager")
    public ResponseEntity<String> createBuildingManager(@RequestBody String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        User dto = mapper.readValue(json, User.class);
        return new ResponseEntity<>(headManagerService.createBuildingManager(dto), HttpStatus.OK);
    }

    @GetMapping("/RevenueFromAllBuilding")
    public ResponseEntity<BuildingManagerDTO>RevenueFromEachBuilding(){
        return new ResponseEntity<>(headManagerService.RevenueFromAllBuilding(), HttpStatus.OK);
    }

    @GetMapping("/findByIdManager/{idUser}")
    public ResponseEntity<SecurityDTO> findByIdManager(@PathVariable("idUser") String idUser)
    {
        return new ResponseEntity<>(headManagerService.findByIdManager(idUser), HttpStatus.OK);
    }

    @GetMapping("/ImportStatisticInvoice")
    public ResponseEntity<List<StatisticDTO>> ImportStatisticInvoice(@RequestParam("time") String time)
    {
        return new ResponseEntity<>(statisticInvoiceService.ImportStatisticInvoice(time), HttpStatus.OK);
    }
}
