package com.mistra.userservice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018-10-11
 * Time: 下午4:41
 * Description: 参数验证DTO
 */
@Data
@ApiModel("参数验证DTO")
public class ParameterVerificationDTO {

    @ApiModelProperty("数字,大于5")
    @NotNull
    @Min(5)
    @Max(10)
    private Integer number;

    @ApiModelProperty("邮箱")
    @Email
    private String email;

    @ApiModelProperty("字符串,长度 6-20")
    @NotBlank
    private String str;

    @ApiModelProperty("集合List")
    @NotEmpty
    private List<String> stringList;

}
