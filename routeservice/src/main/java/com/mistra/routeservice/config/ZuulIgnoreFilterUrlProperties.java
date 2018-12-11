package com.mistra.routeservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018/12/11
 * Time: 10:43
 * Description:
 */
@Component
@ConfigurationProperties(prefix = "zuul-filter")
public class ZuulIgnoreFilterUrlProperties {

    /**
     * 需要忽略过滤验证的请求路径
     */
    List<String> url;

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }
}
