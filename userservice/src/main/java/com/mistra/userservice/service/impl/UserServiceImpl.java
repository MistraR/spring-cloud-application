package com.mistra.userservice.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mistra.base.result.*;
import com.mistra.userservice.dao.UserMapper;
import com.mistra.userservice.entity.User;
import com.mistra.userservice.vo.LoginDTO;
import com.mistra.userservice.vo.RegisterDTO;
import com.mistra.userservice.vo.TokenDTO;
import com.mistra.userservice.vo.UserDTO;
import com.mistra.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午10:35
 * Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public GenericResult<TokenDTO> login(LoginDTO loginDTO) {
        User user = userMapper.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (user != null) {

        }
        return Results.failedGeneric(ResultCode.ERROR, ResultMessage.PASSWORD_ERROR);
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
