package com.demo.service.Impl;

import com.demo.entity.Area;
import com.demo.entity.Building;
import com.demo.entity.Manager;
import com.demo.repository.BuildingRepository;
import com.demo.repository.ManagerRepository;
import com.demo.service.BuildingService;
import com.demo.utils.request.AreaAPI;
import com.demo.utils.request.BuildingAPI;
import com.demo.utils.request.BuildingDTO;
import com.demo.utils.response.BuildingResponseDTO;
import com.demo.utils.response.ManagerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.demo.service.Impl.ManageServiceImpl.mapperedToManager;

@Controller
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Override
    public BuildingResponseDTO save(BuildingDTO dto) {
        Building building = new Building();
        building.setId_Building(dto.getId_Building());
        building.setIncome(dto.getIncome());
        building.setNumber_Of_Area(dto.getNumber_Of_Area());
        List<Manager> list_manager = managerRepository.findAll();
        List<Building> List_building = buildingRepository.findAll();
        building.setManager(list_manager.get(list_manager.size() - 1));
        return mapperedToBuilding(buildingRepository.save(building));
    }

    @Override
    public Optional<BuildingResponseDTO> findById(String Id_Building) {
        return Optional.of(mapperedToBuilding(buildingRepository.findById(Id_Building).get()));
    }

    @Override
    public List<BuildingResponseDTO> findAll() {
        return buildingRepository.findAll().stream().map(b -> mapperedToBuilding(b)).collect(Collectors.toList());
    }

    @Override
    public BuildingResponseDTO update(BuildingDTO dto, String Id_Building) {
        Building building = buildingRepository.findById(Id_Building).get();
        building.setId_Building(dto.getId_Building());
        building.setIncome(dto.getIncome());
        building.setNumber_Of_Area(dto.getNumber_Of_Area());
        List<Manager> list_manager = managerRepository.findAll();
        List<Building> List_building = buildingRepository.findAll();
        if(List_building.size() == 0)
        {
            building.setManager(list_manager.get(0));
        }
        else
        {
            building.setManager(list_manager.get(List_building.size()));
        }
        return mapperedToBuilding(buildingRepository.save(building));
    }

    @Override
    public String delete(String Id_Building) {
        buildingRepository.deleteById(Id_Building);
        return "delete successfully";
    }

    private BuildingResponseDTO mapperedToBuilding(Building dto)
    {
        BuildingResponseDTO building = new BuildingResponseDTO();
        building.setId_Building(dto.getId_Building());
        building.setIncome(dto.getIncome());
        building.setNumber_Of_Area(dto.getNumber_Of_Area());
        building.setManager(mapperedToManager(dto.getManager()));
//        List<Manager> list_manager = managerRepository.findAll();
//        List<Building> List_building = buildingRepository.findAll();
//        if(List_building.size() == 0)
//        {
//            building.setManager(mapperedToManager(list_manager.get(0)));
//        }
//        else
//        {
//            building.setManager(mapperedToManager(list_manager.get(List_building.size())));
//        }
        return building;
    }

    public static BuildingResponseDTO staticmapperedToBuilding(Building dto, ManagerResponseDTO manager)
    {
        BuildingResponseDTO building = new BuildingResponseDTO();
        building.setId_Building(dto.getId_Building());
        building.setIncome(dto.getIncome());
        building.setNumber_Of_Area(dto.getNumber_Of_Area());
        building.setManager(manager);
        return building;
    }

    @Override
    public List<BuildingAPI> findAllBuilding() {
        List<Building> buildingList = buildingRepository.findAll();
        List<BuildingAPI>list = new ArrayList<>();
        for (Building building : buildingList)
        {
            list.add(new BuildingAPI(building.getId_Building(), building.getNumber_Of_Area(), building.getIncome(),
                    building.getManager().getIdUser()));
        }
        return list;
    }
}
