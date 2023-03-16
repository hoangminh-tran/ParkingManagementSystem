package com.demo.service.Impl;

import com.demo.entity.Customer;
import com.demo.entity.Resident;
import com.demo.entity.User;
import com.demo.repository.CustomerRepository;
import com.demo.repository.ResidentRepository;
import com.demo.repository.UserRepository;
import com.demo.service.LoginService;
import com.demo.utils.response.LoginAPI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ResidentRepository residentRepository;

    @Override
    public LoginAPI checkLoginAccount(String username, String password) {
        LoginAPI loginAPI = new LoginAPI();
        User user = userRepository.findUserByUsernameAndPassword(username, password);
        if(user != null)
        {
            Customer customer = customerRepository.findById(username).orElse(null);
            Resident resident = residentRepository.findById(username).orElse(null);
            if(customer != null)
            {
                if(customer.isStatus_Account() == true)
                {
                    loginAPI.setMessage("Your Customer Account has been banned");
                }
                else
                {
                    loginAPI = new LoginAPI(user.getId(), user.getFullname(), user.getPassword(), user.isGender(),
                            user.getDateofbirth(), user.getEmail(), user.getPhone(), customer.isStatus_Account(), "Login Customer Successfully");
                }
            }
            if(resident != null)
            {
                if(resident.isStatus_Account() == true)
                {
                    loginAPI.setMessage("Your Resident Account has been banned");
                }
                else
                {
                    loginAPI = new LoginAPI(user.getId(), user.getFullname(), user.getPassword(), user.isGender(),
                        user.getDateofbirth(), user.getEmail(), user.getPhone(), resident.isStatus_Account(), "Login Resident Successfully");
                }
            }
            if(resident == null && customer == null)
            {
                loginAPI.setMessage("Manager Can not be Login here");
            }
        }
        else
        {
            loginAPI.setMessage("Invalid Password or Username");
        }
        return loginAPI;
    }
}
