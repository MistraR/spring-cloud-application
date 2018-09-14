package com.mistra.userservice.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mistra.base.result.*;
import com.mistra.userservice.base.PageCondition;
import com.mistra.userservice.dao.UserMapper;
import com.mistra.userservice.entity.User;
import com.mistra.userservice.service.AuthorizationService;
import com.mistra.userservice.vo.LoginDTO;
import com.mistra.userservice.vo.RegisterDTO;
import com.mistra.userservice.vo.TokenDTO;
import com.mistra.userservice.vo.UserDTO;
import com.mistra.userservice.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;


/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午10:35
 * Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthorizationService authorizationService;

    @Override
    public GenericResult<TokenDTO> login(LoginDTO loginDTO) {
        User user = userMapper.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (user != null) {
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(authorizationService.generateToken(user.getId().toString()));
            return Results.successGeneric(tokenDTO);
        }
        return Results.failedGeneric(ResultCode.ERROR, ResultMessage.PASSWORD_ERROR);
    }

    @Override
    public Result register(RegisterDTO registerDTO) {
        Assert.notNull(registerDTO.getEmail(), "Email is null");
        Assert.notNull(registerDTO.getName(), "Name is null");
        Assert.notNull(registerDTO.getPassword(), "Password is null");
        logger.info("开始注册-->email:{}, password:{}", registerDTO.getEmail(), registerDTO.getPassword());
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        user.setUserName(registerDTO.getName());
        user.setCreateTime(new Date(System.currentTimeMillis()));
        user.setUpdateTime(new Date(System.currentTimeMillis()));
        userMapper.insert(user);
        logger.info("注册成功-->email:{}", registerDTO.getEmail());
        return Results.success();
    }

    @Override
    public GenericResult<Page<UserDTO>> getUserList(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public GenericResult<Page<UserDTO>> getSelectList(UserDTO userDTO, PageCondition pageCondition) {
        Page<User> userPage = new Page<>(pageCondition.getPageNumber(),pageCondition.getPageSize());
        Wrapper<User> userWrapper = new EntityWrapper<User>().like(StringUtils.isNotBlank(userDTO.getUserName()),"name",userDTO.getUserName());
        userPage = selectPage(userPage,userWrapper);
        return null;
    }
}
