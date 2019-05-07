package com.mistra.userservice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mistra.userservice.core.annotation.MistraResponseBody;
import com.mistra.userservice.core.model.PageQueryCondition;
import com.mistra.userservice.dto.LoginDTO;
import com.mistra.userservice.dto.RegisterDTO;
import com.mistra.userservice.dto.UserDTO;
import com.mistra.userservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @ Author: WangRui
 * @ Date: 2018-09-14
 * Time: 上午9:39
 * Description:
 */
@Api("用户模块Controller")
@RequestMapping("/user")
@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/returnBoolean")
    @ApiOperation("返回Boolean")
    @MistraResponseBody
    public Boolean returnBoolean() {
        return true;
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    @ApiImplicitParam(name = "registerDTO", dataType = "RegisterDTO", value = "用户注册信息", required = true)
    @MistraResponseBody
    public void register(@Valid @RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
    }

    @PostMapping(value = "/login")
    @ApiOperation("用户登录，登录成功则返回token")
    @MistraResponseBody
    public void shiroLogin(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        userService.login(loginDTO, httpServletResponse,httpServletRequest);
    }

    /**
     * * 使用mybatis-plus自带的分页插件查询，返回结果转换为DTO类
     *
     * @param userDTO userDTO
     * @param pageQueryCondition PageQueryCondition
     * @return Page<UserDTO>
     */
    @GetMapping("/selectList")
    @ApiOperation("带查询条件的分页列表---mybatis-plus分页插件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userDTO", dataType = "UserDTO", value = "筛选条件", required = true),
            @ApiImplicitParam(name = "pageQueryCondition", dataType = "PageQueryCondition", value = "分页参数", required = true)
    })
    @MistraResponseBody
    public IPage<UserDTO> getSelectList(@Valid @RequestBody UserDTO userDTO, @Valid @RequestBody PageQueryCondition pageQueryCondition) {
        return userService.getSelectList(userDTO, pageQueryCondition);
    }

    /**
     * Shiro注解的使用
     */
    @RequiresRoles("admin")
    @RequiresPermissions("create")
    @GetMapping(value = "/shiro/create")
    @MistraResponseBody
    public String create() {
        return "Create success!";
    }

    @RequiresRoles("admin")
    @RequiresPermissions("delete")
    @GetMapping(value = "/shiro/delete")
    @MistraResponseBody
    public String delete() {
        return "Delete success!";
    }

}
