package com.mistra.userservice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午1:34
 * Description:
 */
@Data
@ApiModel(value = "UserDTO",description = "用户信息")
public class UserDTO {

    @ApiModelProperty(name = "email",value = "邮箱")
    private String email;

    @ApiModelProperty(name = "userName",value = "用户姓名")
    private String userName;
}
