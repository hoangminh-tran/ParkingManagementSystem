package com.demo.service.Impl;

import com.demo.entity.Customer;
import com.demo.entity.Resident;
import com.demo.entity.User;
import com.demo.repository.ResidentRepository;
import com.demo.repository.UserRepository;
import com.demo.service.ResidentService;
import com.demo.utils.request.ResidentDTO;
import com.demo.utils.request.UserAPI;
import com.demo.utils.response.ResidentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.demo.service.Impl.UserServiceImpl.mapperedToUserResponse;

@Controller
public class ResidentServiceImpl implements ResidentService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ResidentRepository residentRepository;

    @Override
    public ResidentResponseDTO save(ResidentDTO dto) {
        Resident resident = new Resident();
        resident.setStatus_Account(dto.isStatus_Account());
        resident.setIdUser(dto.getIdUser());
        resident.setUser(userRepository.findById(dto.getIdUser()).get());
        return mapperedToResident(residentRepository.save(resident));
    }

    @Override
    public Optional<ResidentResponseDTO> findById(String IdUser) {
        return Optional.of(mapperedToResident(residentRepository.findById(IdUser).get()));
    }

    @Override
    public List<ResidentResponseDTO> findAll() {
        return residentRepository.findAll().stream().map(r -> mapperedToResident(r)).collect(Collectors.toList());
    }

    @Override
    public ResidentResponseDTO update(ResidentDTO dto, String IdUser) {
        Resident resident = residentRepository.findById(IdUser).get();
        resident.setStatus_Account(dto.isStatus_Account());
        resident.setIdUser(dto.getIdUser());
        resident.setUser(userRepository.findById(dto.getIdUser()).get());
        return mapperedToResident(residentRepository.save(resident));
    }

    @Override
    public String delete(String IdUser) {
        residentRepository.deleteById(IdUser);
        return "deleted succesfully";
    }

    private ResidentResponseDTO mapperedToResident(Resident resident)
    {
        ResidentResponseDTO dto =  new ResidentResponseDTO();
        dto.setIdUser(resident.getIdUser());
        dto.setUser(mapperedToUserResponse(resident.getUser()));
        dto.setStatus_Account(resident.isStatus_Account());
        return dto;
    }

    @Override
    public List<UserAPI> findResidentAll() {
        List<UserAPI> list = new ArrayList<>();
        List<User>ListResident = userRepository.findAllResidentByQuery();
        for (User user : ListResident)
        {
            Resident resident = residentRepository.findById(user.getId()).get();
            list.add(new UserAPI(user.getId(), user.getFullname(), user.getPassword(), user.isGender(), user.getDateofbirth(),
                    user.getEmail(), user.getPhone(), resident.isStatus_Account()));
        }
        return list;
    }

    @Override
    public UserAPI findByIdResident(String idCustomer) {
        Resident resident = residentRepository.findById(idCustomer).get();
        User user = userRepository.findById(idCustomer).get();
        if(user != null && resident != null)
        {
            return new UserAPI(user.getId(), user.getFullname(), user.getPassword(), user.isGender(), user.getDateofbirth(),
                    user.getEmail(), user.getPhone(), resident.isStatus_Account());
        }
        return null;
    }
}