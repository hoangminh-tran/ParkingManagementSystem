package com.demo.service;

import com.demo.entity.User;
import com.demo.utils.request.SecurityDTO;
import com.demo.utils.response.ResponseCustomerInfoSlot;
import com.demo.utils.request.UpdateDTO;
import com.demo.utils.request.UserAPI;
import com.demo.utils.response.InvoiceCustomerResponse;
import com.demo.utils.response.InvoiceResidentResponse;
import com.demo.utils.response.ResponseResidentInfoSlot;

import java.util.List;

public interface SecurityService {
    List<UserAPI> getAllCustomerFromBuilding(String Id_Building);

    List<User> getAllResidentFromBuilding(String Id_Building);

    InvoiceCustomerResponse searchCustomerInvoiceId(String Id_C_Invoice);

    List<InvoiceCustomerResponse> findAllCustomerInvoice();

    InvoiceResidentResponse searchResidentInvoiceId(String Id_R_Invoice);

    List<InvoiceResidentResponse> searchResidentInvoiceByTypeOfPayment(String TypeOfPayment);
    InvoiceResidentResponse searchResidentInvoiceIdByResident(String id_Resident);

    List<InvoiceCustomerResponse> searchCustomerInvoiceByCustomer(String id_Customer);

    List<InvoiceCustomerResponse> searchCustomerInvoiceByTypeOfPayment(String TypeOfPayment);

    List<InvoiceResidentResponse> findAllResidentInvoice();

    User createNewResident(User dto);

    User createNewCustomer(User dto);

    User updateCustomer_Resident(String idUser, UpdateDTO dto);

    ResponseCustomerInfoSlot getCustomerInfoOfSlot(String id_Building, String id_C_slot);

    ResponseResidentInfoSlot getResidentInfoOfSlot(String id_Building, String id_R_slot);



}
