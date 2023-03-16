package com.demo.repository;

import com.demo.entity.Customer_Slot;
import com.demo.entity.Resident_Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Resident_Slot_Repository extends JpaRepository<Resident_Slot, Long> {
    @Query(
            value = "select * from resident_slot where id_resident = ?1", nativeQuery = true
    )
    Resident_Slot findResidentSlotByIdResident(String idUser);

    @Query(
            value = "select * from resident_slot where id_resident = ?1", nativeQuery = true
    )
    List<Resident_Slot> findAllResidentSlotByIdResident(String idUser);

    @Query(
            value = "select r.* \n" +
                    "from area a join resident_slot r on r.id_area =  a.id_area \n" +
                    " join building b on a.id_building = b.id_building where r.id_r_slot = ?1 and b.id_building = ?2",
            nativeQuery = true
    )
    Resident_Slot findResidentSlot(String id_r_slot, String id_building);
    @Query(
            value = "select r.* \n" +
                    "from area a join resident_slot r on r.id_area =  a.id_area \n" +
                    " join building b on a.id_building = b.id_building where b.id_building = ?1",
            nativeQuery = true
    )
    List<Resident_Slot> findAllSlotOfEachBuilding(String id_Building);

}
