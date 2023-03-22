package com.demo.service;


import com.demo.utils.response.FeeResponse;
import com.demo.utils.response.PaymentCustomerReponseDTO;
import com.demo.utils.response.PaymentResidentResponseDTO;


public interface MailService {


    String forgot_password(String id_User);


    String invoiceCustomer(String id_User, PaymentCustomerReponseDTO dto);


    String invoiceResident(String id_User, PaymentResidentResponseDTO dto);

    String feeCustomerExpired(String id_User, FeeResponse dto);

    String feeResidentExpired(String id_User, FeeResponse dto);
}

