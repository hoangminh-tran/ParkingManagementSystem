package com.demo.controller;
import com.demo.service.CustomerExpiredService;
import com.demo.service.MailService;
import com.demo.utils.request.MailDTO;
import com.demo.utils.request.PaymentCustomerMail;
import com.demo.utils.response.FeeResponse;
import com.demo.utils.response.PaymentCustomerReponseDTO;
import com.demo.utils.response.PaymentResidentResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailController {
    private final MailService mailService;

    private final CustomerExpiredService customerExpiredService;

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> filterRequest(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        MailDTO dto = mapper.readValue(json, MailDTO.class);
        return new ResponseEntity<>(mailService.forgot_password(dto.getId_User()), HttpStatus.OK);
    }

    @PostMapping("/invoiceCustomer")
    public ResponseEntity<String> invoiceCustomer(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        PaymentCustomerMail dto = mapper.readValue(json, PaymentCustomerMail.class);
        return new ResponseEntity<>(mailService.invoiceCustomer(dto) , HttpStatus.OK);
    }

    @PostMapping("/invoiceResident")
    public ResponseEntity<String> invoiceResident(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        PaymentResidentResponseDTO dto = mapper.readValue(json, PaymentResidentResponseDTO.class);
        return new ResponseEntity<>(mailService.invoiceResident(dto) , HttpStatus.OK);
    }

    @PostMapping("/feeCustomerExpired")
    public ResponseEntity<String> feeCustomerExpired(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        FeeResponse dto = mapper.readValue(json, FeeResponse.class);
        return new ResponseEntity<>(mailService.feeCustomerExpired(dto) , HttpStatus.OK);
    }

    @PostMapping("/feeResidentExpired")
    public ResponseEntity<String> feeResidentExpired(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        FeeResponse dto = mapper.readValue(json, FeeResponse.class);
        return new ResponseEntity<>(mailService.feeResidentExpired(dto) , HttpStatus.OK);
    }
}