package com.mistra.userservice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午10:29
 * Description:
 */
@Data
@ApiModel(description = "登录token")
public class TokenDTO {

    @ApiModelProperty(value = "用户token，在header使用时需要key值为Token，加上'Bearer '前缀")
    private String token;

    @Override
    public String toString() {
        return "TokenDTO{" +
                "token='" + token + '\'' +
                '}';
    }
}
