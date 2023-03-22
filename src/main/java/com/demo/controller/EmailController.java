package com.demo.controller;
import com.demo.service.MailService;
import com.demo.utils.request.MailDTO;
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


    @PostMapping("/forgotPassword")
    public ResponseEntity<String> filterRequest(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        MailDTO dto = mapper.readValue(json, MailDTO.class);
        return new ResponseEntity<>(mailService.forgot_password(dto.getId_User()), HttpStatus.OK);
    }

    @PostMapping("/invoiceCustomer")
    public ResponseEntity<String> invoiceCustomer(@RequestBody String json, HttpSession session) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        MailDTO dto = mapper.readValue(json, MailDTO.class);
        PaymentCustomerReponseDTO dto1 =  (PaymentCustomerReponseDTO) session.getAttribute("InvoiceCustomer");
        System.out.println(dto1);
        session.removeAttribute("InvoiceCustomer");
        return new ResponseEntity<>(mailService.invoiceCustomer(dto.getId_User(), dto1) , HttpStatus.OK);
    }


    @PostMapping("/invoiceResident")
    public ResponseEntity<String> invoiceResident(@RequestBody String json, HttpSession session) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        MailDTO dto = mapper.readValue(json, MailDTO.class);
        PaymentResidentResponseDTO dto1 =  (PaymentResidentResponseDTO) session.getAttribute("InvoiceResident");
        session.removeAttribute("InvoiceResident");
        return new ResponseEntity<>(mailService.invoiceResident(dto.getId_User(), dto1) , HttpStatus.OK);
    }

    @PostMapping("/feeCustomerExpired")
    public ResponseEntity<String> feeCustomerExpired(@RequestBody String json, HttpSession session) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        MailDTO dto = mapper.readValue(json, MailDTO.class);
        FeeResponse dto1 =  (FeeResponse) session.getAttribute("FeeCustomerExpired");
        session.removeAttribute("FeeCustomerExpired");
        return new ResponseEntity<>(mailService.feeCustomerExpired(dto.getId_User(), dto1) , HttpStatus.OK);
    }

    @PostMapping("/feeResidentExpired")
    public ResponseEntity<String> feeResidentExpired(@RequestBody String json, HttpSession session) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        MailDTO dto = mapper.readValue(json, MailDTO.class);
        FeeResponse dto1 =  (FeeResponse) session.getAttribute("FeeResidentExpired");
        session.removeAttribute("FeeResidentExpired");
        return new ResponseEntity<>(mailService.feeResidentExpired(dto.getId_User(), dto1) , HttpStatus.OK);
    }
}