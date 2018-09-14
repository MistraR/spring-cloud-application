package com.mistra.userservice.service.impl;

import com.mistra.base.result.GenericResult;
import com.mistra.base.result.PaginationResult;
import com.mistra.base.result.Result;
import com.mistra.userservice.vo.LoginDTO;
import com.mistra.userservice.vo.RegisterDTO;
import com.mistra.userservice.vo.TokenDTO;
import com.mistra.userservice.vo.UserDTO;
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

    @Override
    public Result register(RegisterDTO registerDTO) {
        return null;
    }

    @Override
    public GenericResult<PaginationResult<UserDTO>> getUserList(int pageNumber, int pageSize) {
        return null;
    }
}
