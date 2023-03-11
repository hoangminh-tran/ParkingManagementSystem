package com.demo.service.Impl;

import com.demo.entity.Area;
import com.demo.repository.AreaRepository;
import com.demo.repository.BuildingRepository;
import com.demo.service.AreaService;
import com.demo.utils.request.AreaAPI;
import com.demo.utils.request.AreaDTO;
import com.demo.utils.response.AreaResponseDTO;
import com.demo.utils.response.ManagerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.demo.service.Impl.BuildingServiceImpl.staticmapperedToBuilding;
import static com.demo.service.Impl.ManageServiceImpl.mapperedToManager;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    AreaRepository areaRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Override
    public AreaResponseDTO save(AreaDTO dto) {
        Area area = new Area();
        area.setArea_name(dto.getArea_name());
        area.setType_of_area(dto.getType_of_area());
        area.setNumber_Of_Slot(dto.getNumber_Of_Slot());
        area.setBuilding(buildingRepository.findById(dto.getId_Building()).get());
        return mapperedToArea(areaRepository.save(area));
    }

    @Override
    public Optional<AreaResponseDTO> findById(Long Id_Area) {
        return Optional.empty();
    }

    @Override
    public List<AreaResponseDTO> findAll() {
        return null;
    }

    @Override
    public AreaResponseDTO update(AreaDTO dto, Long Id_Area) {
        return null;
    }

    @Override
    public String delete(Long Id_Area) {
        return null;
    }

    private AreaResponseDTO mapperedToArea(Area area)
    {
        AreaResponseDTO dto = new AreaResponseDTO();
        dto.setArea_name(area.getArea_name());
        dto.setNumber_Of_Slot(area.getNumber_Of_Slot());
        dto.setType_of_area(area.getType_of_area());
        ManagerResponseDTO manager =  mapperedToManager(buildingRepository.findById(area.getBuilding().getId_Building()).get().getManager());
        dto.setBuilding(staticmapperedToBuilding(buildingRepository.findById(area.getBuilding().getId_Building()).get(), manager));
        return dto;
    }

    public static AreaResponseDTO staticmapperedToArea(Area area, ManagerResponseDTO manager)
    {
        AreaResponseDTO dto = new AreaResponseDTO();
        dto.setId_Area(area.getId_Area());
        dto.setArea_name(area.getArea_name());
        dto.setNumber_Of_Slot(area.getNumber_Of_Slot());
        dto.setType_of_area(area.getType_of_area());
        dto.setBuilding(staticmapperedToBuilding(area.getBuilding(), manager));
        return dto;
    }

    @Override
    public List<AreaAPI> findAllArea() {
        List<Area> areaList = areaRepository.findAll();
        List<AreaAPI>list = new ArrayList<>();
        for (Area area : areaList)
        {
            list.add(new AreaAPI(area.getId_Area(), area.getType_of_area(), area.getNumber_Of_Slot(),
                    area.getArea_name(), area.getBuilding().getId_Building()));
        }
        return list;
    }
}
