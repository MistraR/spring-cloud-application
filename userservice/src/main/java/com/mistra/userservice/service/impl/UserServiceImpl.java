package com.mistra.userservice.service.impl;

import com.mistra.base.result.GenericResult;
import com.mistra.userservice.dto.LoginDTO;
import com.mistra.userservice.dto.TokenDTO;
import com.mistra.userservice.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午10:35
 * Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public GenericResult<TokenDTO> login(LoginDTO loginDTO) {
        return null;
    }
}
