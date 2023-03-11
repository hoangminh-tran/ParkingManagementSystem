package com.demo.repository;

import com.demo.entity.Resident_Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Invoice_R_Repository extends JpaRepository<Resident_Invoice, String> {

    @Query
            (value = "select ri.* from resident_invoice ri join payment_r pr on pr.id_payment = ri.id_payment " +
                    "where pr.id_payment = ?1", nativeQuery = true)
    Resident_Invoice findResident_InvoiceByResidentPayment(String id_Payment);
}
