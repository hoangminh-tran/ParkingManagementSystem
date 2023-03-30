package com.demo.service;


import com.demo.utils.request.PaymentCustomerMail;
import com.demo.utils.response.FeeResponse;
import com.demo.utils.response.PaymentCustomerReponseDTO;
import com.demo.utils.response.PaymentResidentResponseDTO;


public interface ThymeleafService {
    String createContentForgotPassword(String password);


    String createContentInvoiceCustomer(PaymentCustomerMail dto);


    String createContentInvoiceResident(PaymentResidentResponseDTO dto);

    String createContentFeeCustomerExpired(FeeResponse dto);


    String createContentFeeResidentExpired(FeeResponse dto);

}

