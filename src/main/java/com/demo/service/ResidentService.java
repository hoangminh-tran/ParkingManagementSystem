package com.demo.service;

import com.demo.utils.request.ResidentDTO;
import com.demo.utils.request.UserAPI;
import com.demo.utils.response.ResidentResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ResidentService {
    ResidentResponseDTO save(ResidentDTO dto);

    Optional<ResidentResponseDTO> findById(String IdUser);

    List<ResidentResponseDTO> findAll();

    ResidentResponseDTO update(ResidentDTO dto, String IdUser);

    String delete (String IdUser);

    UserAPI findByIdResident(String idCustomer);

    List<UserAPI> findResidentAll();
}
