package com.rest.api.controller;

import com.rest.api.service.UserService;
import com.rest.api.utils.request.UserDTO;
import com.rest.api.utils.response.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/save")
    public ResponseEntity<UserResponseDTO> save(@RequestBody  UserDTO dto)
    {
        return new ResponseEntity<>(userService.save(dto), HttpStatus.OK);
    }

    @GetMapping("/findById")
    public ResponseEntity<Optional<UserResponseDTO>> findById(@RequestParam("id") String id)
    {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponseDTO>> findAll()
    {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> update(@RequestBody UserDTO dto,@RequestParam("id") String id)
    {
        return new ResponseEntity<>(userService.update(dto, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> update(@RequestParam("id") String id)
    {
        return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
    }
}
