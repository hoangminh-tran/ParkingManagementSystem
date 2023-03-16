package com.demo.service;

import com.demo.entity.User;
import com.demo.utils.request.RevenueDTO;
import com.demo.utils.request.SecurityDTO;
import com.demo.utils.request.UpdateDTO;
import com.demo.utils.request.UserAPI;
import com.demo.utils.response.BuildingManagerResponse;

import java.util.List;

public interface BuildingManagerService {
    List<SecurityDTO> findAllSecurity();

    SecurityDTO BanOrUnbanSecurity(String idUser, boolean status);

    SecurityDTO updateSecurity(String idUser, UpdateDTO dto);

    String createSecurity(User user);

    List<RevenueDTO> RevenueFromEachBuilding();

    SecurityDTO findByIdSecurity(String id_Manager);
}
