package com.demo.service;

import com.demo.entity.User;
import com.demo.utils.request.BuildingManagerDTO;
import com.demo.utils.request.SecurityDTO;
import com.demo.utils.request.UpdateDTO;
import com.demo.utils.response.BuildingManagerResponse;

import java.util.List;

public interface HeadManagerService {
    List<SecurityDTO> findAllBuildingManager();

    SecurityDTO BanOrUnbanBuildingManager(String idUser, boolean status);

    SecurityDTO updateBuildingManager(String idUser, UpdateDTO dto);

    String createBuildingManager(User user);

    BuildingManagerDTO RevenueFromAllBuilding();

    SecurityDTO findByIdBuildingManager(String IdBuildingManager);

    List<SecurityDTO> listAllManager();

    SecurityDTO findByIdManager(String idUser);
}
