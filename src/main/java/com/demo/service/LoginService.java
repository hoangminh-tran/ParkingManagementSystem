package com.demo.service;

import com.demo.utils.response.LoginAPI;

public interface LoginService {
    LoginAPI checkLoginAccount(String username, String password);
}
