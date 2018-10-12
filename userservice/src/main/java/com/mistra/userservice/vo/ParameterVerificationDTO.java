package com.mistra.userservice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;
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

    @ApiModelProperty("数字,大于5小于10")
    @NotNull
//    @Min(value = 6, message = "number必须大于5")
//    @Max(value = 10, message = "number必须小于10")
    @Range(min = 6, max = 10, message = "number必须大于6小于30")
    private Integer number;

    @ApiModelProperty("邮箱")
    @Email(message = "邮箱格式错误")
    private String email;

    @ApiModelProperty("字符串,长度 6-20")
    @NotBlank
    @Length(max = 20, min = 6, message = "pattern字符串长度必须为6-20")
    @Pattern(regexp = "^[a-zA-Z_]\\w{4,19}$", message = "pattern字符串必须以字母下划线开头，可由字母数字下划线组成")
    private String pattern;

    @ApiModelProperty("集合List不能为空")
    @NotEmpty(message = "stringList不能为空")
    private List<String> stringList;

    @ApiModelProperty("只能为null")
    @Null(message = "isNull只能为null")
    private String isNull;

    @ApiModelProperty("必须为false")
    @AssertFalse(message = "isFalse必须为false")
    private Boolean isFalse;

    @ApiModelProperty("必须为true")
    @AssertTrue(message = "isTrue必须为true")
    private Boolean isTrue;

    @ApiModelProperty("必须是一个将来的日期")
    @Future(message = "future必须是一个将来的日期")
    private Date future;

    /**
     * 嵌套实体类验证必须加@Valid注解
     */
    @ApiModelProperty("嵌套实体类验证DTO")
    @Valid
    @NotNull(message = "paraVerifyNestingDTO不能为空")
    private ParaVerifyNestingDTO paraVerifyNestingDTO;

    @ApiModelProperty("嵌套实体类验证DTO List")
    @Valid
    @NotNull(message = "paraVerifyNestingDTOList不能为空")
    private List<ParaVerifyNestingDTO> paraVerifyNestingDTOList;

}
