package com.demo.repository;

import com.demo.entity.Customer_Invoice;
import com.demo.entity.Payment_C;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Invoice_C_Repository extends JpaRepository<Customer_Invoice, String> {
    @Query
    (value = "select ci.* from payment_c pc join customer_invoice ci on pc.id_payment = ci.id_payment " +
            "where ci.id_payment = ?1", nativeQuery = true)
    Customer_Invoice findCustomer_Invoice_By_Id_Payment(String id_Payment);

    @Modifying
    @Query
   (value = "update customer_invoice set status = ?1 where id_payment = ?2", nativeQuery = true)
    int updateStatusInvoice(boolean status, String id_payment);
}
