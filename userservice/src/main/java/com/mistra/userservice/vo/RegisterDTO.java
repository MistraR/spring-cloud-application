package com.mistra.userservice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午1:25
 * Description:
 */
@Data
@ApiModel(value = "RegisterDTO",description = "用户注册信息")
public class RegisterDTO {

    @ApiModelProperty(name = "email",value = "邮箱",required = true)
    @Email(message = "邮箱格式输入不正确")
    @NotBlank
    private String email;

    @ApiModelProperty(name = "userName",value = "密码",required = true)
    @NotBlank
    @Length(max = 20,message = "用户名不能超过20个字符")
    private String userName;

    @ApiModelProperty(name = "password",value = "密码",required = true)
    @NotBlank
    @Length(max = 32,min = 6)
    private String password;
}
