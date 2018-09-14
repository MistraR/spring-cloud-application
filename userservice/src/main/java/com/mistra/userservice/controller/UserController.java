package com.mistra.userservice.controller;

import com.mistra.base.result.GenericResult;
import com.mistra.base.result.PaginationResult;
import com.mistra.base.result.Result;
import com.mistra.userservice.vo.LoginDTO;
import com.mistra.userservice.vo.RegisterDTO;
import com.mistra.userservice.vo.TokenDTO;
import com.mistra.userservice.vo.UserDTO;
import com.mistra.userservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午9:39
 * Description:
 */
@Api("用户模块controller")
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation("用户注册")
    @ApiImplicitParam(name = "registerDTO", dataType = "RegisterDTO", value = "用户注册信息", required = true)
    public Result register(@Valid @RequestBody RegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }

    @GetMapping("/login")
    @ApiOperation("登录")
    public GenericResult<TokenDTO> login(@Valid LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @GetMapping("/list")
    @ApiOperation("获取用户分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", dataType = "int", value = "当前页码", required = true),
            @ApiImplicitParam(name = "pageSize", dataType = "int", value = "分页大小", required = true)})
    public GenericResult<PaginationResult<UserDTO>> getUserList(@RequestParam(defaultValue = "1") @Min(0) int pageNumber,
                                                                @RequestParam(defaultValue = "10") @Min(0) int pageSize) {
        return userService.getUserList(pageNumber, pageSize);
    }

}
