package com.demo.service.Impl;

import com.demo.entity.*;
import com.demo.repository.Invoice_R_Repository;
import com.demo.repository.Payment_R_Repository;
import com.demo.repository.ResidentRepository;
import com.demo.repository.Resident_Slot_Repository;
import com.demo.service.ResidentExpiredService;
import com.demo.utils.response.ExpiredResponse;
import com.demo.utils.response.FeeResponse;
import com.demo.utils.response.InvoiceResidentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.demo.entity.Money.*;

@Service
public class ResidentExpiredServiceImpl implements ResidentExpiredService {

    @Autowired
    ResidentRepository residentRepository;

    @Autowired
    Payment_R_Repository paymentRRepository;

    @Autowired
    Invoice_R_Repository invoice_r_repository;

    @Override
    public List<InvoiceResidentResponse> findAllResidentInvoiceByResidentID(String id) {
        Optional<Resident> re = residentRepository.findById(id);
        List<InvoiceResidentResponse> RIList = null;
        if (re != null) {
            List<Payment_R> pr = paymentRRepository.findAllPaymentByResident(id);
            if(pr == null){
                return null;
            }
            for (Payment_R payment_r : pr) {
                Resident_Invoice ri = invoice_r_repository.findResident_InvoiceByResidentPayment(payment_r.getId_Payment());
                if(ri == null){
                    continue;
                }
                InvoiceResidentResponse irr = new InvoiceResidentResponse(
                        ri.getId_R_Invoice(),
                        payment_r.getId_Payment(),
                        payment_r.getType(),
                        ri.isStatus(),
                        ri.getTotal_Of_Money(),
                        ri.getTime(),
                        id);
                if (RIList == null) {
                    RIList = new ArrayList<>();
                }
                if (ri != null)
                    RIList.add(irr);
            }
        }
        return RIList;
    }

    @Autowired
    Resident_Slot_Repository resident_slot_repository;

    @Override
    public List<ExpiredResponse> checkExpired(String id, List<InvoiceResidentResponse> resident_invoiceList, String time) {
        List<ExpiredResponse> expiredResidentResponseList = null;
        if(resident_invoiceList == null){
            return null;
        }
        for (InvoiceResidentResponse ri : resident_invoiceList) {
            Date end_date = ri.getTime();
            Date current_date = new Date();
            Payment_R pr = paymentRRepository.findPaymentByInvoiceId(ri.getId_R_Invoice());
            if(pr == null){
                return null;
            }
            String id_invoice = pr.getResident_invoice().getId_R_Invoice();
            Resident_Slot rs = resident_slot_repository.findResidentSlotByIdResident(pr.getResident().getIdUser());
            if(rs == null){
                return null;
            }

            TimeZone vietnamTimeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
            Calendar calendar = Calendar.getInstance(vietnamTimeZone);
            calendar.setTime(current_date);
            int current_month = calendar.get(Calendar.MONTH) + 1; // Note: Calendar.MONTH is zero-based, so add 1
            int current_day = calendar.get(Calendar.DAY_OF_MONTH);

            calendar.setTime(end_date);
            int end_month = calendar.get(Calendar.MONTH) + 1 + 1;
            int end_day = calendar.get(Calendar.DAY_OF_MONTH);

            System.out.println(current_day + ":" + current_month);
            System.out.println(end_day + ":" + end_month);

            int expired = 1;
            double fine = 0;
            boolean warning = false;
            double type_money = 1;
            System.out.println(rs.getType_Of_Vehicle());
            if (rs.getType_Of_Vehicle().equals("Motor")) {
                type_money = MOTO_MONEY_BY_DAY * 1.5;
            } else if (rs.getType_Of_Vehicle().equals("Car")) {
                type_money = CAR_MONEY_BY_DAY * 1.5;
            } else if (rs.getType_Of_Vehicle().equals("Bike")) {
                type_money = BIKE_MONEY_BY_DAY * 1.5;
            }
            System.out.println(CAR_MONEY_BY_DAY);
            if (end_month == current_month) {
                expired = (current_day - end_day < 0) ? 0 : (current_day - end_day);
                fine = expired * type_money;
                if(expired > 0)
                    warning = true;
            } else if (end_month < current_month) {
                expired = (current_day - end_day) + (current_month - end_month) * 31;
                fine = expired * type_money;
                warning = true;
            }
            if (warning == true) {
                String current_time = Integer.parseInt(time.substring(0, time.indexOf('a'))) + ":" + current_date.getMinutes();
                String end_time = "00:00";
                ExpiredResponse ex = new ExpiredResponse(pr.getResident().getIdUser()
                        , id_invoice
                        , current_date
                        , current_time
                        , end_date
                        , end_time
                        , expired
                        , fine
                        , warning);
                if (expiredResidentResponseList == null) {
                    expiredResidentResponseList = new ArrayList<>();
                }
                if (ex != null) {
                    expiredResidentResponseList.add(ex);
                    Resident_Invoice invoice = invoice_r_repository.findResident_InvoiceByResidentPayment(pr.getId_Payment());
                    invoice.setStatus(false);
                    invoice_r_repository.save(invoice);
                }
            }
        }
        return expiredResidentResponseList;
    }

    @Override
    public FeeResponse getResidentFee(String id_invoice, String time) {
        Payment_R pr = paymentRRepository.findPaymentByInvoiceId(id_invoice);
        if(pr == null){
            return null;
        }
        Resident_Invoice ri = invoice_r_repository.findResident_InvoiceByResidentPayment(pr.getId_Payment());
        if(ri == null){
            return null;
        }
        Resident re = pr.getResident();
        if(re == null){
            return  null;
        }
        List<ExpiredResponse> expiredList = checkExpired(re.getIdUser(), findAllResidentInvoiceByResidentID(re.getIdUser()), time);
        if(expiredList == null){
            return null;
        }
        FeeResponse fee = null;
//        if (ri.getPayment_r().getType().equals("Banking")) {
//            for (ExpiredResponse er : expiredList) {
//                if (er.getId_invoice().equals(id_invoice) && er.isWarning()) {
//                    fee = new FeeResponse(id_invoice,
//                            re.getIdUser(),
//                            er.isWarning(),
//                            er.getCurrent_date(),
//                            er.getCurrent_time(),
//                            er.getEnd_date(),
//                            er.getEnd_time(),
//                            er.getExpired(),
//                            ri.getTotal_Of_Money(),
//                            er.getFine(),
//                            er.isWarning(),
//                            ri.getTotal_Of_Money() + er.getFine(),
//                            ri.getTotal_Of_Money(),
//                            er.getFine());
//                    break;
//                }
//            }
//        } else {
            for (ExpiredResponse er : expiredList) {
                if (er.getId_invoice().equals(id_invoice) && er.isWarning()) {
                    Date d = er.getCurrent_date();
                    d.setMonth(er.getEnd_date().getMonth() + 1);
                    er.setEnd_date(d);
                    fee = new FeeResponse(id_invoice,
                            re.getIdUser(),
                            er.isWarning(),
                            er.getCurrent_date(),
                            er.getCurrent_time(),
                            er.getEnd_date(),
                            er.getEnd_time(),
                            er.getExpired(),
                            ri.getTotal_Of_Money(),
                            er.getFine(),
                            er.isWarning(),
                            ri.getTotal_Of_Money() + er.getFine(),
                            0.0,
                            ri.getTotal_Of_Money() + er.getFine());
                    break;
                }
            }
//        }
        return fee;
    }

    @Override
    public String payFeeR(String id_invoice, String time) {
        Payment_R pr = paymentRRepository.findPaymentByInvoiceId(id_invoice);
        if(pr == null){
            return null;
        }
        Resident_Invoice ri = invoice_r_repository.findResident_InvoiceByResidentPayment(pr.getId_Payment());
        if(ri == null){
            return null;
        }
        Resident re = pr.getResident();
        if(re == null){
            return null;
        }
        List<ExpiredResponse> expiredList = checkExpired(re.getIdUser(), findAllResidentInvoiceByResidentID(re.getIdUser()), time);
        if(expiredList == null){
            return null;
        }
        FeeResponse fee = null;
        if (ri.isStatus() == true) {
            for (ExpiredResponse er : expiredList) {
                if (er.getId_invoice().equals(id_invoice) && er.isWarning()) {
//                    ri.setStatus(true);
//                    er.setWarning(false);
                    break;
                }
            }
        } else {
            for (ExpiredResponse er : expiredList) {
                if (er.getId_invoice().equals(id_invoice) && er.isWarning()) {
//                    ri.setStatus(true);
//                    er.setWarning(false);
                    Resident_Invoice invoice = invoice_r_repository.findResident_InvoiceByResidentPayment(pr.getId_Payment());
                    invoice.setStatus(true);
                    Date end_date = ri.getTime();
                    end_date.setMonth(ri.getTime().getMonth() + 1);
                    ri.setTime(end_date);
                    invoice_r_repository.save(invoice);
                    break;
                }
            }
        }
        return "OK";
    }
}