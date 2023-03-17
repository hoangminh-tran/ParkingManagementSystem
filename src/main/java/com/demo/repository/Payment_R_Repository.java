package com.demo.repository;

import com.demo.entity.Payment_C;
import com.demo.entity.Payment_R;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Payment_R_Repository extends JpaRepository<Payment_R, String> {
    @Query
            (value = "select pr.* from payment_r pr join resident_slot rs on rs.id_resident = pr.id_resident " +
                    "where rs.id_r_slot = ?1 and rs.id_area = ?2", nativeQuery = true)
    Payment_R findPayment_R_By_Resident_Slot(String id_R_Slot, Long id_Area);

    @Query
            (value = "select pr.* from payment_r pr join resident_slot rs on rs.id_resident = pr.id_resident " +
                    "where rs.id_r_slot = ?1 and rs.id_area = ?2 and rs.id_resident = ?3", nativeQuery = true)
    List<Payment_R> findListPayment_R_By_ResidentSlotAndResidentName(String id_R_Slot, Long id_Area, String id_resident);

    @Query(value = "select * from payment_r where type_of_payment = ?1", nativeQuery = true)
    List<Payment_R> findPaymentR_By_TypeOfPayment(String typeOfPayment);

    @Query(value = "select pr.* from payment_r pr join resident_invoice ri " +
            "on pr.id_payment = ri.id_payment where ri.id_r_invoice = ?1", nativeQuery = true)
    Payment_R findPaymentByInvoiceId(String id_R_invoice);

    @Query
            (value = "select pr.* from payment_r pr join resident_slot rs on rs.id_resident = pr.id_resident where pr.id_resident = ?1", nativeQuery = true)
    List<Payment_R> findAllPaymentByResident(String id_Resident);
}
