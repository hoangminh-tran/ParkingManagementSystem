package com.demo.service;

import com.demo.utils.request.PaymentResidentDTO;
import com.demo.utils.response.PaymentResidentResponseDTO;

public interface PaymentResidentService {
    PaymentResidentResponseDTO save(PaymentResidentDTO dto);

    PaymentResidentResponseDTO findPayment();
}
