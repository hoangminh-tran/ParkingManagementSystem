package com.demo.repository;

import com.demo.entity.Booking;
import com.demo.entity.Customer_Slot;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(
            value = "select b.* from booking b join customer_slot c on b.id_index = c.id_index\n" +
                    "where c.id_c_slot = ?1 and c.id_area = ?2 and b.is_deleted = 0 and b.is_enabled = 1 and b.is_checkout = 0", nativeQuery = true
    )
    Booking findIdBookingByCustomerSlot(String id_C_Slot, Long id_Area);

    @Query
    (value = "select b.* from booking b join payment_c pc on b.id_booking = pc.id_booking " +
            "where pc.id_payment = ?1 and b.is_deleted = 0 and b.is_enabled = 1 and b.is_checkout = 0", nativeQuery = true)
    Booking findBookingByIdPayment(String id_Payment);

    @Query
            (value = "select b.* from booking b join payment_c pc on b.id_booking = pc.id_booking " +
                    "where pc.id_payment = ?1", nativeQuery = true)
    Booking findAllBookingByIdPayment(String id_Payment);

    @Query
            (value = "select b.* from booking b  " +
                    "where b.id_customer = ?1 and b.is_deleted = 0 and b.is_enabled = 1 and b.is_checkout = 0", nativeQuery = true)
    List<Booking> findBookingByCustomer(String id_Customer);

    @Query(
            value = "select b.* from booking b join customer_slot c on b.id_index = c.id_index\n" +
                    "where c.id_area = ?1 and b.is_deleted = 0 and b.is_enabled = 1 and b.is_checkout = 0", nativeQuery = true
    )
    List<Booking> findIdBookingByIdArea(Long id_Area);

    @Query
    (
            value = "select b.* from booking b join customer_slot cs on b.id_index = cs.id_index " +
                    "where cs.id_area = ?1 and cs.id_c_slot = ?2 and b.id_customer = ?3 and b.is_deleted = 0 and b.is_enabled = 1 and b.is_checkout = 0", nativeQuery = true
    )
    Booking findBookingByCustomerAndCustomer_slot(Long id_area, String id_C_slot, String id_User);

    @Query
            (value = "select b.* from booking b  " +
                    "where b.id_customer = ?1", nativeQuery = true)
    List<Booking> findBookingByCustomerID(String id_Customer);

//    @Transactional
    @Modifying
    @Query
            (value = "update booking set is_checkout = ?1 where id_booking = ?2", nativeQuery = true)
    int updateStatusCheckout(boolean status, Long id_booking);

    @Query
            (value = "select b.* from booking b where b.is_deleted = 0 and b.is_enabled = 1 and b.is_checkout = 0", nativeQuery = true)
    List<Booking> getAllBooking();
}
