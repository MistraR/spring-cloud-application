package com.mistra.userservice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午1:34
 * Description:
 */
@ApiModel(value = "UserDTO",description = "用户信息")
public class UserDTO {

    @ApiModelProperty(name = "email",value = "邮箱")
    private String email;

    @ApiModelProperty(name = "userName",value = "用户姓名")
    private String userName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
