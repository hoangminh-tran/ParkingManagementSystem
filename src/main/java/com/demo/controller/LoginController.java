package com.demo.controller;

import com.demo.entity.Customer;
import com.demo.entity.User;
import com.demo.repository.CustomerRepository;
import com.demo.repository.UserRepository;
import com.demo.service.CustomerExpiredService;
import com.demo.service.LoginService;
import com.demo.service.MailService;
import com.demo.service.ResidentExpiredService;
import com.demo.utils.request.BookingCustomerDTO;
import com.demo.utils.request.BuildingDTO;
import com.demo.utils.request.MailDTO;
import com.demo.utils.response.ExpiredResponse;
import com.demo.utils.response.FeeResponse;
import com.demo.utils.response.LoginAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    @Autowired
    LoginService loginService;

    private final UserRepository userRepository;

    private  final CustomerRepository customerRepository;

    private final MailService mailService;

    private final CustomerExpiredService customerExpiredService;

    private final ResidentExpiredService residentExpiredService;



    @GetMapping("/loginAccount")
    public ResponseEntity<LoginAPI> checkLogin(@RequestParam("username") String username,
                                                @RequestParam("password") String password)
    {
//        System.out.println(username + " " + password);
        return new ResponseEntity<>(loginService.checkLoginAccount(username, password), HttpStatus.OK);
    }

    @PostMapping("/checkLoginCustomerExpireInvoice")
    public ResponseEntity<String>checkLoginCustomerExpireInvoice(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        MailDTO dto = mapper.readValue(json, MailDTO.class);
        List<ExpiredResponse> list = customerExpiredService.checkExpired(dto.getId_User(),
                customerExpiredService.findAllCustomerInvoiceByCustomerID(dto.getId_User()),
                dto.getTime());
        if(list.size() > 0)
        {
            for(ExpiredResponse expiredResponse : list)
            {
                mailService.feeCustomerExpired(customerExpiredService.getCustomerFee(expiredResponse.getId_invoice(), dto.getTime()));
            }
            log.info("Have: " +  list.size() + " Expire");
            return new ResponseEntity<>("Have Expire", HttpStatus.OK);
        }
        log.info("No Expire");
        return new ResponseEntity<>("No Expire", HttpStatus.OK);
    }

    @PostMapping("/checkLoginResidentExpireInvoice")
    public ResponseEntity<String>checkLoginResidentExpireInvoice(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        MailDTO dto = mapper.readValue(json, MailDTO.class);
        List<ExpiredResponse> list = residentExpiredService.checkExpired(dto.getId_User(),
                residentExpiredService.findAllResidentInvoiceByResidentID(dto.getId_User()),
                dto.getTime());
        if(list.size() > 0)
        {
            for(ExpiredResponse expiredResponse : list)
            {
                System.out.println(expiredResponse);
                mailService.feeResidentExpired(residentExpiredService.getResidentFee(expiredResponse.getId_invoice(), dto.getTime()));
            }
            log.info("Have: " +  list.size() + " Expire");
            return new ResponseEntity<>("Have Expire", HttpStatus.OK);
        }
        log.info("No Expire");
        return new ResponseEntity<>("No Expire", HttpStatus.OK);
    }

//    @GetMapping("/loginGoogle")
//    public ResponseEntity<LoginAPI> currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken){
//        String email = oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email").toString();
//        String name = oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name").toString();
//        String password = oAuth2AuthenticationToken.getPrincipal().getAttributes().get("at_hash").toString();
//        System.out.println(email);
//        System.out.println(name);
//        System.out.println(password);
//        User user = new User(email, name, password, true, new Date(), email, "0987654321");
//        User checkUser = userRepository.findCustomerById(email);
//        if(checkUser == null){
//            userRepository.save(user);
//            customerRepository.save(new Customer(email, false, user));
//            return new ResponseEntity<>(loginService.checkLoginAccount(email, password), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(loginService.checkLoginAccount(checkUser.getId(), checkUser.getPassword()), HttpStatus.OK);
//    }
}
