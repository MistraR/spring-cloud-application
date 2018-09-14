package com.mistra.userservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午10:30
 * Description:
 */
@ApiModel(value = "LoginDTO",description = "用户登录信息")
public class LoginDTO {

    @Email
    @ApiModelProperty(name = "userName",value = "用户邮箱",required = true)
    private String userName;

    @NotBlank
    @ApiModelProperty(name = "password",value = "用户密码",required = true)
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
