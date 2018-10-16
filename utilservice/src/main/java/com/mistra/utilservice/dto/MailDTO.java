package com.mistra.utilservice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @Author: WangRui
 * @Date: 2018-10-16
 * Time: 下午2:00
 * Description:
 */
@Data
public class MailDTO {

    /**
     * 发送对象
     */
    @NotEmpty
    private List<String> sendToAddress;

    /**
     * 主题
     */
    @NotNull
    private String subject;

    /**
     * 模板
     */
    @NotNull
    private String template;

    /**
     * 模板参数
     */
    @NotBlank
    private Map<String,Object> paramsMap;
}
