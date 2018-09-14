package com.mistra.userservice.service.impl;

import com.mistra.userservice.service.AuthorizationService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午11:19
 * Description:
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Override
    public String generateToken(String userId) {
        return null;
    }

    @Override
    public String parseToken(String token) {
        return null;
    }

    @Override
    public String getToken(HttpServletRequest httpServletRequest) {
        return null;
    }
}
