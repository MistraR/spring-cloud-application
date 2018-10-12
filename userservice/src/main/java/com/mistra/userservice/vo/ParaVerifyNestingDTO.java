package com.mistra.userservice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: WangRui
 * @Date: 2018-10-12
 * Time: 上午9:22
 * Description: 参数验证 嵌套实体类验证DTO
 */
@Data
@ApiModel("参数验证 嵌套实体类验证DTO")
public class ParaVerifyNestingDTO {

    @ApiModelProperty("nest不能为空")
    @NotBlank(message = "nest不能为空")
    private String nest;
}
