package com.demo.service;

import com.demo.utils.request.CancelPaymentDTO;
import com.demo.utils.request.PaymentCustomerDTO;
import com.demo.utils.response.PaymentCustomerReponseDTO;

public interface PaymentCustomerService {
    PaymentCustomerReponseDTO save(PaymentCustomerDTO dto);

    PaymentCustomerReponseDTO findPayment();

    PaymentCustomerReponseDTO UpdateTypeOfPayment(PaymentCustomerReponseDTO dto);

}
