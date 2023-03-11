package com.demo.service;

import com.demo.utils.request.BuildingAPI;
import com.demo.utils.request.BuildingDTO;
import com.demo.utils.request.CustomerDTO;
import com.demo.utils.response.BuildingResponseDTO;
import com.demo.utils.response.CustomerResponseDTO;

import java.util.List;
import java.util.Optional;

public interface BuildingService {
    BuildingResponseDTO save(BuildingDTO dto);

    Optional<BuildingResponseDTO> findById(String Id_Building);

    List<BuildingResponseDTO> findAll();

    BuildingResponseDTO update(BuildingDTO dto, String Id_Building);

    String delete (String Id_Building);

    List<BuildingAPI> findAllBuilding();
}
