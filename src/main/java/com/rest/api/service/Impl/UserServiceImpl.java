package com.rest.api.service.Impl;

import com.rest.api.entity.User;
import com.rest.api.repository.UserRepository;
import com.rest.api.service.UserService;
import com.rest.api.utils.request.UserDTO;
import com.rest.api.utils.response.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<UserResponseDTO> findById(String id) {
        return Optional.of(mapperedToUserResponse(userRepository.findById(id).get()));
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream().map(c -> mapperedToUserResponse(c)).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO save(UserDTO user) {
        User dto = new User();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setGender(user.isGender());
        dto.setDateofbirth(user.getDateofbirth());
        dto.setPassword(user.getPassword());
        dto.setFullname(user.getFullname());
        dto.setPhone(user.getPhone());
        return mapperedToUserResponse(userRepository.save(dto));
    }

    @Override
    public UserResponseDTO update(UserDTO user, String id) {
        User dto = userRepository.findById(id).get();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setGender(user.isGender());
        dto.setDateofbirth(user.getDateofbirth());
        dto.setPassword(user.getPassword());
        dto.setFullname(user.getFullname());
        dto.setPhone(user.getPhone());
        return mapperedToUserResponse(userRepository.save(dto));
    }

    @Override
    public String delete(String id) {
        userRepository.deleteById(id);
        return "Delete Successfully";
    }

    public static UserResponseDTO mapperedToUserResponse(User user)
    {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setGender(user.isGender());
        dto.setDateofbirth(user.getDateofbirth());
        dto.setPassword(user.getPassword());
        dto.setFullname(user.getFullname());
        dto.setPhone(user.getPhone());
        return dto;
    }
}
