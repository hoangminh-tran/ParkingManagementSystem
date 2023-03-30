package com.demo.service;


import com.demo.utils.request.PaymentCustomerMail;
import com.demo.utils.response.FeeResponse;
import com.demo.utils.response.PaymentCustomerReponseDTO;
import com.demo.utils.response.PaymentResidentResponseDTO;


public interface MailService {


    String forgot_password(String id_User);


    String invoiceCustomer(PaymentCustomerMail dto);


    String invoiceResident(PaymentResidentResponseDTO dto);

    String feeCustomerExpired(FeeResponse dto);

    String feeResidentExpired(FeeResponse dto);
}

