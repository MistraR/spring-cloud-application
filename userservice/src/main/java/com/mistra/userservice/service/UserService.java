package com.mistra.userservice.service;

import com.mistra.base.result.GenericResult;
import com.mistra.base.result.PaginationResult;
import com.mistra.base.result.Result;
import com.mistra.userservice.base.PageQueryCondition;
import com.mistra.userservice.vo.LoginDTO;
import com.mistra.userservice.vo.RegisterDTO;
import com.mistra.userservice.vo.TokenDTO;
import com.mistra.userservice.vo.UserDTO;

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
    GenericResult<TokenDTO> login(LoginDTO loginDTO);

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
    GenericResult<PaginationResult<UserDTO>> getUserList(int pageNumber, int pageSize);

    /**
     * 获取用户列表，带筛选条件
     * @param userDTO
     * @param pageQueryCondition
     * @return
     */
    GenericResult<PaginationResult<UserDTO>> getSelectList(UserDTO userDTO, PageQueryCondition pageQueryCondition);
}
