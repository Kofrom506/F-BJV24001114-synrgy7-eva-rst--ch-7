package com.example.batch7.ch4.service.oauth;

import com.example.batch7.ch4.dto.req.RegisterModel;

import java.security.Principal;
import java.util.Map;

public interface UserService {
    Map registerManual(RegisterModel objModel) ;

    Map registerByGoogle(RegisterModel objModel) ;

    Map getDetailProfile(Principal principal);
}
