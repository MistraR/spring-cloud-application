package com.mistra.userservice.service;

import com.mistra.userservice.base.result.PageResult;
import com.mistra.userservice.base.result.Result;
import com.mistra.userservice.base.model.PageQueryCondition;
import com.mistra.userservice.dto.LoginDTO;
import com.mistra.userservice.dto.RegisterDTO;
import com.mistra.userservice.dto.UserDTO;
import com.mistra.userservice.entity.User;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午10:34
 * Description:
 */
public interface UserService {

    /**
     * 用户登录
     * @param loginDTO
     * @return
     */
    Result login(LoginDTO loginDTO);

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
    Result register(RegisterDTO registerDTO);

    /**
     * 获取用户列表
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageResult<UserDTO> getUserList(int pageNumber, int pageSize);

    /**
     * 获取用户列表，带筛选条件
     * 使用mybatis-plus自带的分页插件查询，返回结果转换为自定义带DTO的PaginationResult
     * @param userDTO
     * @param pageQueryCondition
     * @return
     */
    PageResult<UserDTO> getSelectList(UserDTO userDTO, PageQueryCondition pageQueryCondition);

    /**
     * 获取用户列表，带筛选条件
     * 使用github page-helper分页插件查询，返回结果转换为自定义带DTO的PaginationResult
     * @param userDTO
     * @param pageQueryCondition
     * @return
     */
    PageResult<UserDTO> getSelectList2(UserDTO userDTO, PageQueryCondition pageQueryCondition);
}
