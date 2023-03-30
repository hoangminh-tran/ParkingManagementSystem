package com.demo.service;

import com.demo.utils.request.StatisticDTO;

import java.util.List;

public interface StatisticInvoiceService {
    List<StatisticDTO> ImportStatisticInvoice(String time);
}
