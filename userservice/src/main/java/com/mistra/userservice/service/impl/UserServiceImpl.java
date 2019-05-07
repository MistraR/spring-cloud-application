package com.mistra.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mistra.userservice.core.JWT.JsonWebTokenUtils;
import com.mistra.userservice.core.web.RequestHeaderConstant;
import com.mistra.userservice.core.exception.BusinessErrorCode;
import com.mistra.userservice.core.exception.BusinessException;
import com.mistra.userservice.core.model.PageQueryCondition;
import com.mistra.userservice.dao.SystemPermissionMapper;
import com.mistra.userservice.dao.SystemRoleMapper;
import com.mistra.userservice.dao.UserMapper;
import com.mistra.userservice.dto.LoginDTO;
import com.mistra.userservice.dto.RegisterDTO;
import com.mistra.userservice.dto.UserDTO;
import com.mistra.userservice.entity.SystemRole;
import com.mistra.userservice.entity.User;
import com.mistra.userservice.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @ Author: WangRui
 * @ Date: 2018-09-14
 * Time: 上午10:35
 * Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserMapper userMapper;

    private SystemRoleMapper systemRoleMapper;

    private SystemPermissionMapper systemPermissionMapper;

    private JsonWebTokenUtils jsonWebTokenUtils;

    /**
     * Spring推荐的构造函数注入方式，可以避免NPE,可以在IOC环境外被new,但是如果依赖多的话会显得代码臃肿
     *
     * @param userMapper             UserMapper
     * @param systemRoleMapper       SystemRoleMapper
     * @param systemPermissionMapper SystemPermissionMapper
     * @param jsonWebTokenUtils      JsonWebTokenUtils
     */
    @Autowired
    public UserServiceImpl(UserMapper userMapper, SystemRoleMapper systemRoleMapper, SystemPermissionMapper systemPermissionMapper, JsonWebTokenUtils jsonWebTokenUtils) {
        this.userMapper = userMapper;
        this.systemRoleMapper = systemRoleMapper;
        this.systemPermissionMapper = systemPermissionMapper;
        this.jsonWebTokenUtils = jsonWebTokenUtils;
    }

    /**
     * 登录，shiro验证用户名密码
     *
     * @param loginDTO            LoginDTO
     * @param httpServletResponse HttpServletResponse
     * @param httpServletRequest  HttpServletRequest
     */
    @Override
    public void login(LoginDTO loginDTO, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        try {
            //添加用户认证信息  shiro
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginDTO.getUserName(), loginDTO.getPassword());
            //进行验证，这里可以捕获异常，然后返回对应信息，未抛异常就是验证通过
            subject.login(usernamePasswordToken);
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("name", loginDTO.getUserName()));
            String token = jsonWebTokenUtils.loginGenerateToken(user.getId().toString(), httpServletRequest);
            Cookie cookie = new Cookie(RequestHeaderConstant.HEAD_USER_TOKEN, token);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
        } catch (AuthenticationException a) {
            logger.info("UserServiceImpl.login(),USER_LOGIN_PWD_ERROR_FAIL");
            throw new BusinessException(BusinessErrorCode.USER_LOGIN_PWD_ERROR_FAIL);
        }
    }

    /**
     * 查询用户权限
     *
     * @param userName userName
     * @return User
     */
    @Override
    public User findUserRolePermission(String userName) {
        User user = userMapper.findByUserName(userName);
        List<SystemRole> systemRoles = new ArrayList<>();
        SystemRole systemRole = systemRoleMapper.selectById(user.getSystemRoleId());
        systemRole.setSysPermissionList(systemPermissionMapper.findBySystemRoleId(user.getSystemRoleId()));
        systemRoles.add(systemRole);
        user.setRoleList(systemRoles);
        return user;
    }

    /**
     * 注册
     *
     * @param registerDTO RegisterDTO
     */
    @Override
    public void register(RegisterDTO registerDTO) {
        List<User> userList = userMapper.selectList(new QueryWrapper<User>().eq("email", registerDTO.getEmail()));
        if (!userList.isEmpty()) {
            throw new BusinessException(BusinessErrorCode.EMAIL_EXIST);
        }
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        user.setUserName(registerDTO.getUserName());
        user.setStatus(1);
        user.setCreateTime(new Date(System.currentTimeMillis()).getTime());
        user.setUpdateTime(new Date(System.currentTimeMillis()).getTime());
        userMapper.insert(user);
    }

    /**
     * 分页筛选查询
     *
     * @param userDTO            UserDTO
     * @param pageQueryCondition PageQueryCondition
     * @return IPage
     */
    @Override
    public IPage<UserDTO> getSelectList(UserDTO userDTO, PageQueryCondition pageQueryCondition) {
        Page<UserDTO> userPage = new Page<>();
        QueryWrapper<User> userWrapper = new QueryWrapper<User>().eq(StringUtils.isNotBlank(userDTO.getUserName()), "name", userDTO.getUserName())
                .eq(StringUtils.isNotBlank(userDTO.getEmail()), "email", userDTO.getEmail());
        IPage<User> userIPage = userMapper.selectPage(new Page<>(pageQueryCondition.getPageNumber(), pageQueryCondition.getPageSize()), userWrapper);
        BeanUtils.copyProperties(userIPage, userPage);
        List<UserDTO> userDTOList = userIPage.getRecords().stream().map(this::convertDTO).collect(Collectors.toList());
        userPage.setRecords(userDTOList);
        return userPage;
    }

    /**
     * Entity转换DTO
     *
     * @param entity User
     * @return UserDTO
     */
    private UserDTO convertDTO(User entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(entity.getEmail());
        userDTO.setUserName(entity.getUserName());
        return userDTO;
    }
}
