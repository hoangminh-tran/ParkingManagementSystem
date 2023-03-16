package com.demo.service;

import com.demo.entity.Resident_Invoice;
import com.demo.utils.response.ExpiredResponse;
import com.demo.utils.response.InvoiceResidentResponse;

import java.util.List;

public interface ResidentExpiredService {
    public List<InvoiceResidentResponse> findAllResidentInvoiceByResidentID(String id);
    public List<ExpiredResponse> checkExpired(String id, List<InvoiceResidentResponse> resident_invoiceList);
}