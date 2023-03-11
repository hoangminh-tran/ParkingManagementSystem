package com.demo.service;

import com.demo.utils.request.ManagerDTO;
import com.demo.utils.response.ManagerResponseDTO;


import java.util.List;
import java.util.Optional;

public interface ManageService {
    ManagerResponseDTO save(ManagerDTO dto);

    Optional<ManagerResponseDTO> findById(String IdUser);

    List<ManagerResponseDTO> findAll();

    ManagerResponseDTO update(ManagerDTO dto, String IdUser);

    String delete (String IdUser);
}
