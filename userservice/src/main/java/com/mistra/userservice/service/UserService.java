package com.mistra.userservice.service;

import com.mistra.base.result.GenericResult;
import com.mistra.base.result.PaginationResult;
import com.mistra.base.result.Result;
import com.mistra.userservice.dto.LoginDTO;
import com.mistra.userservice.dto.RegisterDTO;
import com.mistra.userservice.dto.TokenDTO;
import com.mistra.userservice.dto.UserDTO;

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
    GenericResult<PaginationResult<UserDTO>> getUserList(int pageNumber,int pageSize);
}
