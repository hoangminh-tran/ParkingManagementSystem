package com.demo.repository;

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

    @Query(value = "select * from payment_r where type_of_payment = ?1", nativeQuery = true)
    List<Payment_R> findPaymentR_By_TypeOfPayment(String typeOfPayment);
}
