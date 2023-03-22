package com.demo.controller;

import com.demo.service.LoginService;
import com.demo.utils.response.BookingCustomerResponseDTO;
import com.demo.utils.response.LoginAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @GetMapping("/loginAccount")
    public ResponseEntity<LoginAPI> findBooking(@RequestParam("username") String username,
                                                @RequestParam("password") String password)
    {
//        System.out.println(username + " " + password);
        return new ResponseEntity<>(loginService.checkLoginAccount(username, password), HttpStatus.OK);
    }
}
