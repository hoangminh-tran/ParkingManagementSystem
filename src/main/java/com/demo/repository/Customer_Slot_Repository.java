package com.demo.repository;

import com.demo.entity.Booking;
import com.demo.entity.Customer_Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Customer_Slot_Repository extends JpaRepository<Customer_Slot, Long> {

    @Query(
            value = "select c.* \n" +
                    "from area a join customer_slot c on c.id_area =  a.id_area \n" +
                    " join building b on a.id_building = b.id_building where b.id_building = ?1",
            nativeQuery = true
    )
    List<Customer_Slot> findAllSlotOfEachBuilding(String id_Building);
    @Query(
            value = "select c.* \n" +
                    "from area a join customer_slot c on c.id_area =  a.id_area \n" +
                    " join building b on a.id_building = b.id_building where c.id_c_slot = ?1 and b.id_building = ?2",
            nativeQuery = true
    )
    Customer_Slot findCustomerSlot(String id_C_slot, String id_building);

    @Query(
            value = "select c.* from booking b join customer_slot c on b.id_index = c.id_index\n" +
                    "where b.id_booking = ?1", nativeQuery = true
    )
    Customer_Slot findCustomerSlotByIdBooking(Long id_booking);

    @Query(
            value = "select c.* from booking b join customer_slot c on b.id_index = c.id_index\n" +
                    "where b.is_deleted = 0 and b.is_enabled = 1", nativeQuery = true
    )
    List<Customer_Slot> findCustomerSlotWithBooking();


}
