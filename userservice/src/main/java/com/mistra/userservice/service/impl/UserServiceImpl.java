package com.mistra.userservice.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.mistra.base.result.*;
import com.mistra.userservice.base.PageQueryCondition;
import com.mistra.userservice.dao.UserMapper;
import com.mistra.userservice.entity.User;
import com.mistra.userservice.service.AuthorizationService;
import com.mistra.userservice.dto.LoginDTO;
import com.mistra.userservice.dto.RegisterDTO;
import com.mistra.userservice.dto.TokenDTO;
import com.mistra.userservice.dto.UserDTO;
import com.mistra.userservice.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    public Result login(LoginDTO loginDTO) {
        User user = userMapper.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (user != null) {
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(authorizationService.generateToken(user.getId().toString()));
            return RequestResultBuilder.entityResult(tokenDTO);
        }
        return RequestResultBuilder.failed(ResultMessage.PASSWORD_ERROR);
    }

    @Override
    public Result register(RegisterDTO registerDTO) {
        Wrapper<User> userWrapper = new EntityWrapper<User>().like("email", registerDTO.getEmail());
        if (selectList(userWrapper).size() > 0) {
            return RequestResultBuilder.failed(ResultMessage.REPEAT_EMAIL);
        }
        logger.info("开始注册-->email:{}, password:{}", registerDTO.getEmail(), registerDTO.getPassword());
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        user.setUserName(registerDTO.getUserName());
        user.setStatus(1);
        user.setCreateTime(new Date(System.currentTimeMillis()).getTime());
        user.setUpdateTime(new Date(System.currentTimeMillis()).getTime());
        userMapper.insert(user);
        logger.info("注册成功-->email:{}", registerDTO.getEmail());
        return RequestResultBuilder.success();
    }

    @Override
    public PageResult<UserDTO> getUserList(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public PageResult<UserDTO> getSelectList(UserDTO userDTO, PageQueryCondition pageQueryCondition) {
        Page<User> userPage = new Page<>(pageQueryCondition.getPageNumber(), pageQueryCondition.getPageSize());
        Wrapper<User> userWrapper = new EntityWrapper<User>().like(StringUtils.isNotBlank(userDTO.getUserName()), "name", userDTO.getUserName())
                .like(StringUtils.isNotBlank(userDTO.getEmail()), "email", userDTO.getEmail());
        userPage = selectPage(userPage, userWrapper);
        return RequestResultBuilder.pageResult(userPage.getRecords().stream().map(this::convertDTO).collect(Collectors.toList()), userPage.getTotal(),
                userPage.getPages(), userPage.getCurrent(), userPage.getSize());
    }

    /**
     * mybatis-plus的Page类和github-pageHelper的Page类重复了，这里就用全限定名
     *
     * @param userDTO
     * @param pageQueryCondition
     * @return
     */
    @Override
    public PageResult<UserDTO> getSelectList2(UserDTO userDTO, PageQueryCondition pageQueryCondition) {
        //测试JWT认证
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String userId = authorizationService.parseToken(authorizationService.getToken(servletRequestAttributes.getRequest()));
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        //1、当UserDTO含有pageNum和pageSize参数并且都不为空时，直接传入UserDTO也可以实现分页
        List<User> userList1 = userMapper.getSelectList3(userDTO);
        logger.info("userList1.size---------------------------------------" + userList1.size());

        //2、startPage()方法之后紧跟mapper接口查询方法  github-pageHelper
        com.github.pagehelper.Page<User> pageInfo1 = PageHelper.startPage(pageQueryCondition.getPageNumber(), pageQueryCondition.getPageSize());
        List<User> userList2 = userMapper.getSelectList2(userDTO);
        logger.info("userList2.size---------------------------------------" + userList2.size());
        //lambda写法
        com.github.pagehelper.PageInfo<User> pageInfo2 = PageHelper.startPage(pageQueryCondition.getPageNumber(), pageQueryCondition.getPageSize())
                .doSelectPageInfo(() -> userMapper.getSelectList2(userDTO));
        logger.info("userList3.size---------------------------------------" + pageInfo2.getList().size());
        return RequestResultBuilder.pageResult(pageInfo1.getResult().stream().map(this::convertDTO).collect(Collectors.toList()), pageInfo1.getTotal(),
                pageInfo1.getPages(), pageQueryCondition.getPageNumber(), pageQueryCondition.getPageSize());
    }

    /**
     * Entity转换DTO
     * @param entity
     * @return
     */
    private UserDTO convertDTO(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }
}
