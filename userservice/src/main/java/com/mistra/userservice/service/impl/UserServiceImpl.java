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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
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
        Page<User> userPage = new Page<>(pageQueryCondition.getPageNumber(), pageQueryCondition.getPageSize());
        Wrapper<User> userWrapper = new EntityWrapper<User>().like(StringUtils.isNotBlank(userDTO.getUserName()), "name", userDTO.getUserName());
        userPage = selectPage(userPage, userWrapper);
        return Results.successGeneric(pageDataConvert(userPage));
    }

    /**
     * mybatis-plus的Page类和github-pagehelper的Page类重复了，这里就用全限定名
     *
     * @param userDTO
     * @param pageQueryCondition
     * @return
     */
    @Override
    public GenericResult<PaginationResult<UserDTO>> getSelectList2(UserDTO userDTO, PageQueryCondition pageQueryCondition) {
        //测试JWT认证
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String userId = authorizationService.parseToken(authorizationService.getToken(servletRequestAttributes.getRequest()));
        if (StringUtils.isEmpty(userId)){
            return null;
        }
        PaginationResult<UserDTO> userDTOPaginationResult = new PaginationResult();
        //1、配置supportMethodsArguments=true,则支持方法参数上传递分页参数，xml中不需要处理后面两个参数
        List<User> userList1 = userMapper.methodParameter(userDTO, pageQueryCondition.getPageNumber(), pageQueryCondition.getPageSize());
        logger.info("userList1.size---------------------------------------" + userList1.size());
        //2、当UserDTO含有pageNum和pageSize参数并且都不为空时，直接传入UserDTO也可以实现分页
        List<User> userList2 = userMapper.getSelectList3(userDTO);
        logger.info("userList2.size---------------------------------------" + userList2.size());
        //3、startPage()方法之后紧跟mapper接口查询方法
        com.github.pagehelper.Page<User> pageInfo1 = PageHelper.startPage(pageQueryCondition.getPageNumber(), pageQueryCondition.getPageSize());
        //lambda写法
        com.github.pagehelper.PageInfo<User> pageInfo2 = PageHelper.startPage(pageQueryCondition.getPageNumber(), pageQueryCondition.getPageSize())
                .doSelectPageInfo(() -> userMapper.getSelectList2(userDTO));
        List<User> userList3 = userMapper.getSelectList2(userDTO);
        logger.info("userList3.size---------------------------------------" + userList3.size());
        userDTOPaginationResult.setTotalData(pageInfo1.getTotal());
        userDTOPaginationResult.setPageSize(pageQueryCondition.getPageSize());
        userDTOPaginationResult.setTotalPageNumber(pageInfo1.getPages());
        userDTOPaginationResult.setCurrentPageNumber(pageQueryCondition.getPageNumber());
        userDTOPaginationResult.setData(pageInfo1.getResult().stream().map(this::convertDTO).collect(Collectors.toList()));
        return Results.successGeneric(userDTOPaginationResult);
    }

    /**
     * 分页数据DTO转换
     *
     * @param userPage
     * @return
     */
    @Transactional
    public PaginationResult<UserDTO> pageDataConvert(Page<User> userPage) {
        if (userPage.getRecords().size() == 0) {
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
