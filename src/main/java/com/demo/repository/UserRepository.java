package com.demo.repository;

import com.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query
    (
            value = "select u.* from users u join customer c on \n" +
                    "u.id_user = c.id_customer",
            nativeQuery = true
    )
    List<User> findALlCustomer();

    @Query
    (
            value = "select u.* from users u join customer c on \n" +
                    "u.id_user = c.id_customer where u.id_user = ?1",
            nativeQuery = true
    )
    User findCustomerById(String idUser);

    @Query(
            value = "select u.* from users u join manager m on u.id_user = m.id_manager where m.role = ?1"
            , nativeQuery = true
    )
    List<User> findSecurityByIdUser(int role);

    @Query(
            value = "select u.* from users u join manager m on u.id_user = m.id_manager where m.id_manager = ?1 and m.role = ?2"
            , nativeQuery = true
    )
    User findSecurityByIdManager(String id_manager, int role);

    @Query
    (
            value = "select u.* from users u join customer c on \n" +
                    "u.id_user = c.id_customer",
            nativeQuery = true
    )
    List<User> findAllCustomerByQuery();

    @Query
    (
        value = "select u.* from users u join resident r on \n" +
                "u.id_user = r.id_resident",
        nativeQuery = true
    )
    List<User> findAllResidentByQuery();
}
