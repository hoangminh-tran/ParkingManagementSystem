package com.demo.service;

import com.demo.entity.Money;
import com.demo.utils.request.MoneyDTO;
import com.demo.utils.response.MoneyResponseDTO;

public interface MoneyService {
    MoneyResponseDTO saveMoneyFromAPI(MoneyDTO dto);

    MoneyResponseDTO findALlTypeOfMoney();
}
