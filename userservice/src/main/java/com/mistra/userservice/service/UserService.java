package com.mistra.userservice.service;

import com.mistra.base.result.GenericResult;
import com.mistra.userservice.dto.LoginDTO;
import com.mistra.userservice.dto.TokenDTO;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午10:34
 * Description:
 */
public interface UserService {

    GenericResult<TokenDTO> login(LoginDTO loginDTO);
}
