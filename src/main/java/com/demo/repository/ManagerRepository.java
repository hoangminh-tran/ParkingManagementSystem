package com.demo.repository;

import com.demo.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, String> {
    @Query(value = "select * from manager where id_manager = ?1 and role = ?2", nativeQuery = true)
    Manager findManagerByManagerRole(String id_manager, int role);
}
