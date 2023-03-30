package com.demo.service;

import com.demo.utils.request.MailDTO;
import com.demo.utils.response.FeeResponse;
import com.demo.utils.response.LoginAPI;

import java.util.List;

public interface LoginService {
    LoginAPI checkLoginAccount(String username, String password);

    List<FeeResponse> checkLoginExpireInvoice(String id_User, String time);
}
