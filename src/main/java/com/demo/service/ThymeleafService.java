package com.demo.service;


import com.demo.utils.response.FeeResponse;
import com.demo.utils.response.PaymentCustomerReponseDTO;
import com.demo.utils.response.PaymentResidentResponseDTO;


public interface ThymeleafService {
    String createContentForgotPassword(String password);


    String createContentInvoiceCustomer(PaymentCustomerReponseDTO dto);


    String createContentInvoiceResident(PaymentResidentResponseDTO dto);

    String createContentFeeCustomerExpired(FeeResponse dto);


    String createContentFeeResidentExpired(FeeResponse dto);

}

