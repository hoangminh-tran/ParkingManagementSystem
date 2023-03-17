package com.demo.service;

import com.demo.entity.User;
import com.demo.utils.request.*;
import com.demo.utils.response.ResponseCustomerInfoSlot;
import com.demo.utils.response.InvoiceCustomerResponse;
import com.demo.utils.response.InvoiceResidentResponse;
import com.demo.utils.response.ResponseResidentInfoSlot;

import java.util.List;

public interface SecurityService {
    List<UserAPI> getAllCustomerFromBuilding(String Id_Building);

    List<ResidentAPI> getAllResidentFromBuilding(String Id_Building);

    InvoiceCustomerResponse searchCustomerInvoiceId(String Id_C_Invoice);

    List<InvoiceCustomerResponse> findAllCustomerInvoice();

    InvoiceResidentResponse searchResidentInvoiceId(String Id_R_Invoice);

    List<InvoiceResidentResponse> searchResidentInvoiceByTypeOfPayment(String TypeOfPayment);
    InvoiceResidentResponse searchResidentInvoiceIdByResident(String id_Resident);

    List<InvoiceCustomerResponse> searchCustomerInvoiceByCustomer(String id_Customer);

    List<InvoiceCustomerResponse> searchCustomerInvoiceByTypeOfPayment(String TypeOfPayment);

    List<InvoiceResidentResponse> findAllResidentInvoice();

    String createNewResident(User dto);

    String createNewCustomer(User dto);

    User updateCustomer_Resident(String idUser, UpdateDTO dto);

    ResponseCustomerInfoSlot getCustomerInfoOfSlot(String id_Building, String id_C_slot);

    ResponseResidentInfoSlot getResidentInfoOfSlot(String id_Building, String id_R_slot);

    List<UserAPI> searchCustomerByEmail(String email);

    List<UserAPI> searchResidentByEmail(String Email);

    List<UserAPI> searchCustomerByPhone(String phone);

    List<UserAPI> searchResidentByPhone(String phone);

    UserAPI BanOrUnBanCustomer(String id_Customer);

    UserAPI BanOrUnBanResident(String id_Resident);

    String changeStatusInvoiceCustomer(String id_c_invoice);

    String changeStatusInvoiceResident(String id_r_invoice);

    List<CustomerBookingHistory> getCustomerBookingHistory(String id_Customer);

    List<ResidentBookingHistory> getResidentBookingHistory(String id_Resident);
}
