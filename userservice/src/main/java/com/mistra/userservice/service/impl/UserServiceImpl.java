package com.mistra.userservice.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mistra.base.result.*;
import com.mistra.userservice.base.PageQueryCondition;
import com.mistra.userservice.dao.UserMapper;
import com.mistra.userservice.entity.User;
import com.mistra.userservice.service.AuthorizationService;
import com.mistra.userservice.vo.LoginDTO;
import com.mistra.userservice.vo.RegisterDTO;
import com.mistra.userservice.vo.TokenDTO;
import com.mistra.userservice.vo.UserDTO;
import com.mistra.userservice.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


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
    private ModelMapper modelMapper;

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
    public GenericResult<PaginationResult<UserDTO>> getUserList(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public GenericResult<PaginationResult<UserDTO>> getSelectList(UserDTO userDTO, PageQueryCondition pageQueryCondition) {
        Page<User> userPage = new Page<>(pageQueryCondition.getPageNumber(),pageQueryCondition.getPageSize());
        Wrapper<User> userWrapper = new EntityWrapper<User>().like(StringUtils.isNotBlank(userDTO.getUserName()),"name",userDTO.getUserName());
        userPage = selectPage(userPage,userWrapper);
        return Results.successGeneric(pageDataConvert(userPage));
    }

    /**
     * 分页数据DTO转换方法
     * @param userPage
     * @return
     */
    public PaginationResult<UserDTO> pageDataConvert(Page<User> userPage){
        if (userPage.getRecords().size()==0){
            return null;
        }
        PaginationResult<UserDTO> userDTOPaginationResult = new PaginationResult<>();
        List<User> userList = userPage.getRecords();
        List<UserDTO> userDTOList = userList.stream().map(this::convertDTO).collect(Collectors.toList());
        userDTOPaginationResult.setData(userDTOList);
        userDTOPaginationResult.setCurrentPageNumber(userPage.getCurrent());
        userDTOPaginationResult.setTotalPageNumber(userPage.getPages());
        userDTOPaginationResult.setPageSize(userPage.getSize());
        userDTOPaginationResult.setTotalData(userPage.getTotal());
        return userDTOPaginationResult;
    }

    private UserDTO convertDTO(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }
}
