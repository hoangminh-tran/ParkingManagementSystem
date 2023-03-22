package com.demo.service;

import com.demo.utils.response.ExpiredResponse;
import com.demo.utils.response.FeeResponse;
import com.demo.utils.response.InvoiceResidentResponse;

import java.util.List;

public interface ResidentExpiredService {
    public List<InvoiceResidentResponse> findAllResidentInvoiceByResidentID(String id);
    public List<ExpiredResponse> checkExpired(String id, List<InvoiceResidentResponse> resident_invoiceList, String time);
    public FeeResponse getResidentFee(String id_invoice, String time);
    public String payFeeR(String id_invoice, String time);
}