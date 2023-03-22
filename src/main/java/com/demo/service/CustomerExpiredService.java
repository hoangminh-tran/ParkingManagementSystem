package com.demo.service;

import com.demo.entity.Customer_Invoice;
import com.demo.entity.Resident_Invoice;
import com.demo.utils.response.ExpiredResponse;
import com.demo.utils.response.FeeResponse;
import com.demo.utils.response.InvoiceCustomerResponse;

import java.util.List;

public interface CustomerExpiredService {
    public List<InvoiceCustomerResponse> findAllCustomerInvoiceByCustomerID(String id);
    public List<ExpiredResponse> checkExpired(String id, List<InvoiceCustomerResponse> customerInvoices, String time);
    public FeeResponse getCustomerFee(String id_invoice, String time);
    public String payFeeC(String id_invoice, String time);
}