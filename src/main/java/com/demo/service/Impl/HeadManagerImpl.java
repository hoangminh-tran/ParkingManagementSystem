package com.demo.service.Impl;

import com.demo.entity.Building;
import com.demo.entity.Manager;
import com.demo.entity.User;
import com.demo.repository.BuildingRepository;
import com.demo.repository.ManagerRepository;
import com.demo.repository.UserRepository;
import com.demo.service.HeadManagerService;
import com.demo.utils.request.BuildingManagerDTO;
import com.demo.utils.request.SecurityDTO;
import com.demo.utils.request.UpdateDTO;
import com.demo.utils.response.BuildingManagerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HeadManagerImpl implements HeadManagerService {
    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Override
    public List<SecurityDTO> findAllBuildingManager() {
        List<User>list_security = userRepository.findSecurityByIdUser(2);
        List<SecurityDTO> list = new ArrayList<>();
        for(User user : list_security)
        {
            Manager manager = managerRepository.findById(user.getId()).get();
            list.add(new SecurityDTO(user.getId(), user.getFullname(), user.getPassword(), user.isGender(), user.getDateofbirth(),
                    user.getEmail(), user.getPhone(), manager.isStatus_Account(), manager.getRole()));
        }
        return list;
    }

    @Override
    public SecurityDTO BanOrUnbanBuildingManager(String idUser, boolean status) {
        System.out.println(idUser);
        System.out.println(status);
        User user = userRepository.findById(idUser).get();
        Manager manager = managerRepository.findById(idUser).get();
        manager.setIdUser(idUser);
        manager.setStatus_Account(status);
        managerRepository.save(manager);

        return new SecurityDTO(user.getId(), user.getFullname(), user.getPassword(), user.isGender(), user.getDateofbirth(),
                user.getEmail(), user.getPhone(), manager.isStatus_Account(), manager.getRole());
    }

    @Override
    public SecurityDTO updateBuildingManager(String idUser, UpdateDTO user) {
        User dto = userRepository.findSecurityByIdManager(idUser, 2);
        dto.setId(idUser);
        dto.setEmail(user.getEmail());
        dto.setGender(user.isGender());
        dto.setDateofbirth(user.getDateofbirth());
        dto.setPassword(user.getPassword());
        dto.setFullname(user.getFullname());
        dto.setPhone(user.getPhone());
        userRepository.save(dto);
        Manager manager = managerRepository.findById(idUser).get();
        return new SecurityDTO(idUser, user.getFullname(), user.getPassword(), user.isGender(), user.getDateofbirth(),
                user.getEmail(), user.getPhone(), manager.isStatus_Account(), manager.getRole());
    }

    @Override
    public String createBuildingManager(User user) {
        Manager buildingManager = managerRepository.findManagerByManagerRole(user.getId(), 2);
        if(buildingManager != null)
        {
            return "Building Manager is existed in Database!!!!!";
        }
        User dto = new User();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setGender(user.isGender());
        dto.setDateofbirth(user.getDateofbirth());
        dto.setPassword(user.getPassword());
        dto.setFullname(user.getFullname());
        dto.setPhone(user.getPhone());
        userRepository.save(dto);
        managerRepository.save(new Manager(user.getId(), userRepository.findById(user.getId()).get(), 2));
        Manager manager = managerRepository.findById(user.getId()).get();
        return "Create Building Manager Successfully";
    }

    @Override
    public BuildingManagerDTO RevenueFromAllBuilding() {
        List<Building>list = buildingRepository.findAll();
        double income = 0;
        for (Building building : list) {
            income += building.getIncome();
        }
        List<User>list_buildingManager = userRepository.findSecurityByIdUser(1);
        return new BuildingManagerDTO(list_buildingManager.get(0).getId(), income);
    }

    @Override
    public SecurityDTO findByIdBuildingManager(String IdBuildingManager) {
        Manager manager = managerRepository.findById(IdBuildingManager).get();
        User user = userRepository.findSecurityByIdManager(IdBuildingManager, 2);
        return new SecurityDTO(user.getId(), user.getFullname(), user.getPassword(), user.isGender(), user.getDateofbirth(),
                user.getEmail(), user.getPhone(), manager.isStatus_Account(), manager.getRole());
    }

    @Override
    public List<SecurityDTO> listAllManager() {
        List<SecurityDTO>list = new ArrayList<>();
        List<Manager> managers = managerRepository.findAll();
        for(Manager manager : managers)
        {
            User user = userRepository.findById(manager.getUser().getId()).get();
            list.add(new SecurityDTO(user.getId(), user.getFullname(), user.getPassword(), user.isGender(), user.getDateofbirth(),
                    user.getEmail(), user.getPhone(), manager.isStatus_Account(), manager.getRole()));
        }
        return list;
    }

    @Override
    public SecurityDTO findByIdManager(String idUser) {

        Manager manager = managerRepository.findById(idUser).get();
        User user = userRepository.findById(idUser).get();
        if (manager != null && user != null)
        {
            return new SecurityDTO(user.getId(), user.getFullname(), user.getPassword(), user.isGender(), user.getDateofbirth(),
                    user.getEmail(), user.getPhone(), manager.isStatus_Account(), manager.getRole());
        }
        return null;
    }
}
