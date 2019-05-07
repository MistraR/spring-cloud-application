package com.mistra.userservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mistra.userservice.core.model.PageQueryCondition;
import com.mistra.userservice.dto.LoginDTO;
import com.mistra.userservice.dto.RegisterDTO;
import com.mistra.userservice.dto.UserDTO;
import com.mistra.userservice.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ Author: WangRui
 * @ Date: 2018-09-14
 * Time: 上午10:34
 * Description:
 */
public interface UserService {

    /**
     * 用户登录
     * @param loginDTO
     * @return
     */
    void login(LoginDTO loginDTO, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest);

    /**
     * 通过用户账号查询用户的角色以及对于的角色权限
     * @param userName
     * @return
     */
    User findUserRolePermission(String userName);

    /**
     * 用户注册
     * @param registerDTO
     * @return
     */
    void register(RegisterDTO registerDTO);

    /**
     * 获取用户列表，带筛选条件
     * 使用mybatis-plus自带的分页插件查询，返回结果转换为自定义带DTO的PaginationResult
     * @param userDTO
     * @param pageQueryCondition
     * @return
     */
    IPage<UserDTO> getSelectList(UserDTO userDTO, PageQueryCondition pageQueryCondition);

}
