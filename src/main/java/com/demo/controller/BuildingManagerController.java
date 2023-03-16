package com.demo.controller;

import com.demo.entity.User;
import com.demo.service.BuildingManagerService;
import com.demo.utils.request.RevenueDTO;
import com.demo.utils.request.SecurityDTO;
import com.demo.utils.request.UpdateDTO;
import com.demo.utils.response.BuildingManagerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buildingManager")
public class BuildingManagerController {
    @Autowired
    BuildingManagerService buildingManagerService;

    @GetMapping("/findAllSecurity")
    public ResponseEntity<List<SecurityDTO>>findAllSecurity(){
        return new ResponseEntity<>(buildingManagerService.findAllSecurity(), HttpStatus.OK);
    }

    @PutMapping("/BanOrUnbanSecurity")
    public ResponseEntity<SecurityDTO>BanOrUnbanSecurity(@RequestParam("idUser") String idUser,
                                                                     @RequestParam("status") boolean status){
        return new ResponseEntity<>(buildingManagerService.BanOrUnbanSecurity(idUser, status), HttpStatus.OK);
    }

    @PutMapping("/updateSecurity")
    public ResponseEntity<SecurityDTO> updateSecurity(@RequestBody String json, @RequestParam("idUser") String idUser) throws  Exception {
        ObjectMapper mapper = new ObjectMapper();
        UpdateDTO dto = mapper.readValue(json, UpdateDTO.class);
        return new ResponseEntity<>(buildingManagerService.updateSecurity(idUser, dto), HttpStatus.OK);
    }

    @PostMapping("/createSecurity")
    public ResponseEntity<String> createSecurity(@RequestBody String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        User dto = mapper.readValue(json, User.class);
        return new ResponseEntity<>(buildingManagerService.createSecurity(dto), HttpStatus.OK);
    }

    @GetMapping("/RevenueFromEachBuilding")
    public ResponseEntity<List<RevenueDTO>>RevenueFromEachBuilding(){
        return new ResponseEntity<>(buildingManagerService.RevenueFromEachBuilding(), HttpStatus.OK);
    }
}
