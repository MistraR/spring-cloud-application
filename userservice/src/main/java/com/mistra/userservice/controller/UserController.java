package com.mistra.userservice.controller;

import com.mistra.base.result.PageResult;
import com.mistra.base.result.RequestResultBuilder;
import com.mistra.base.result.Result;
import com.mistra.userservice.base.model.PageQueryCondition;
import com.mistra.userservice.dto.LoginDTO;
import com.mistra.userservice.dto.RegisterDTO;
import com.mistra.userservice.dto.UserDTO;
import com.mistra.userservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    @ApiOperation("访问成功测试")
    public Result test() {
        return RequestResultBuilder.success();
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    @ApiImplicitParam(name = "registerDTO", dataType = "RegisterDTO", value = "用户注册信息", required = true)
    public Result register(@Valid @RequestBody RegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }

    @PostMapping(value = "/login")
    public Result shiroLogin(@RequestBody LoginDTO loginDTO) {
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginDTO.getUserName(), loginDTO.getPassword());
        //进行验证，这里可以捕获异常，然后返回对应信息
        subject.login(usernamePasswordToken);
        return userService.login(loginDTO);
    }

    @GetMapping("/error")
    @ApiOperation("权限认证失败")
    public Result login() {
        return RequestResultBuilder.failed("鉴权失败!");
    }

    @GetMapping("/list")
    @ApiOperation("获取用户分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", dataType = "int", value = "当前页码", required = true),
            @ApiImplicitParam(name = "pageSize", dataType = "int", value = "分页大小", required = true)})
    public PageResult<UserDTO> getUserList(@RequestParam(defaultValue = "1") @Min(0) int pageNumber, @RequestParam(defaultValue = "10") @Min(0) int pageSize) {
        return null;
    }

    /**
     * * 使用mybatis-plus自带的分页插件查询，返回结果转换为自定义带DTO的PaginationResult
     *
     * @param userDTO
     * @param pageQueryCondition
     * @return
     */
    @GetMapping("/selectList")
    @ApiOperation("带查询条件的分页列表---mybatis-plus分页插件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userDTO", dataType = "UserDTO", value = "筛选条件", required = true),
            @ApiImplicitParam(name = "pageQueryCondition", dataType = "PageQueryCondition", value = "分页参数", required = true)
    })
    public PageResult<UserDTO> getSelectList(@Valid @RequestBody UserDTO userDTO, @Valid @RequestBody PageQueryCondition pageQueryCondition) {
        return userService.getSelectList(userDTO, pageQueryCondition);
    }

    /**
     * * 使用github page-helper分页插件查询，返回结果转换为自定义带DTO的PaginationResult
     *
     * @param userDTO
     * @param pageQueryCondition
     * @return
     */
    @GetMapping("/selectList2")
    @ApiOperation("带查询条件的分页列表---github分页插件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userDTO", dataType = "UserDTO", value = "筛选条件", required = true),
            @ApiImplicitParam(name = "pageQueryCondition", dataType = "PageQueryCondition", value = "分页参数", required = true)
    })
    public PageResult<UserDTO> getSelectList2(@Valid UserDTO userDTO, @Valid PageQueryCondition pageQueryCondition) {
        return userService.getSelectList2(userDTO, pageQueryCondition);
    }

    /**
     * Shiro注解的使用
     */
    @RequiresRoles("admin")
    @RequiresPermissions("create")
    @GetMapping(value = "/shiro/create")
    public String create() {
        return "Create success!";
    }


    @RequiresRoles("admin")
    @RequiresPermissions("delete")
    @GetMapping(value = "/shiro/delete")
    public String delete() {
        return "Delete success!";
    }

}
