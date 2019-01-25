package com.mistra.userservice.base.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018/12/11
 * Time: 11:10
 * Description:
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class InterceptorIgnoreUrl {

    /**
     * 拦截器需要忽略的路径
     */
    List<String> ignoreUrl;
}
