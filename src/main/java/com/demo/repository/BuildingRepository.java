package com.demo.repository;

import com.demo.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, String> {
    @Query
            (value = "select b.* from building b join area a on b.id_building = a.id_building " +
                    " join customer_slot cs on cs.id_area = a.id_area " +
                    " where cs.id_index = ?1", nativeQuery = true)
    Building findBuildingByCustomerSlot(Long id_Index);

    @Query
            (value = "select b.* from building b join area a on b.id_building = a.id_building " +
                    " join resident_slot rs on rs.id_area = a.id_area " +
                    " where rs.id_index = ?1", nativeQuery = true)
    Building findBuildingByResidentSlot(Long id_Index);
}
