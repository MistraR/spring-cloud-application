package com.mistra.userservice.controller;

import com.mistra.base.result.GenericResult;
import com.mistra.userservice.dto.LoginDTO;
import com.mistra.userservice.dto.TokenDTO;
import com.mistra.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午9:39
 * Description:
 */
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public GenericResult<TokenDTO> login(@Valid LoginDTO loginDTO){
        return userService.login(loginDTO);
    }
}
