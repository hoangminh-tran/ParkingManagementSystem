package com.demo.service;

import com.demo.utils.request.AreaAPI;
import com.demo.utils.request.AreaDTO;
import com.demo.utils.response.AreaResponseDTO;

import java.util.List;
import java.util.Optional;

public interface AreaService {
    AreaResponseDTO save(AreaDTO dto);

    Optional<AreaResponseDTO> findById(Long Id_Area);

    List<AreaResponseDTO> findAll();

    AreaResponseDTO update(AreaDTO dto, Long Id_Area);

    String delete (Long Id_Area);

    List<AreaAPI> findAllArea();
}
