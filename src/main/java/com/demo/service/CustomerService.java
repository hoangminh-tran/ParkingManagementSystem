package com.demo.service;

import com.demo.entity.User;
import com.demo.utils.request.CustomerDTO;
import com.demo.utils.request.ManagerDTO;
import com.demo.utils.request.UserAPI;
import com.demo.utils.response.CustomerResponseDTO;
import com.demo.utils.response.ManagerResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    CustomerResponseDTO save(CustomerDTO dto);

    Optional<CustomerResponseDTO> findById(String IdUser);

    List<CustomerResponseDTO> findAll();

    CustomerResponseDTO update(CustomerDTO dto, String IdUser);

    String delete (String IdUser);

    UserAPI findByIdCustomer(String idCustomer);

    List<UserAPI> findCustomerAll();
}
