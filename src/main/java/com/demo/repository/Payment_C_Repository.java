package com.demo.repository;

import com.demo.entity.Payment_C;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Payment_C_Repository extends JpaRepository<Payment_C, String>{
    @Query
    (
            value = "select pc.* from payment_c pc join booking b on b.id_booking = pc.id_booking " +
                    "where b.id_booking = ?1", nativeQuery = true
    )
    Payment_C findPayment_C_By_Id_Booking(Long id_Booking);

    @Modifying
    @Query
    (value = "update payment_c set type_of_payment = ?1 where id_booking = ?2", nativeQuery = true)
    int updateTypeOfPayment(String type_of_payment, Long id_Booing);


    @Query
    (
            value = "select pc.* from payment_c pc  " +
                    "where pc.type_of_payment = ?1", nativeQuery = true
    )
    List<Payment_C> findPayment_C_By_TypeOfPayment(String typeOfPayment);

    @Query(
            value = "select pc.* from payment_c pc join booking b on b.id_booking = pc.id_booking\n" +
                    "                    join customer_slot c on c.id_index = b.id_index\n" +
                    "                    where id_area = ?1", nativeQuery = true
    )
    List<Payment_C> findPayment_C_By_Id_Area(Long id_Area);

    @Query(value = "select pc.* from payment_c pc join customer_invoice ci " +
            "on pc.id_payment = ci.id_payment where ci.id_c_invoice = ?1", nativeQuery = true)
    Payment_C findPaymentByInvoiceId(String id_C_invoice);
}
