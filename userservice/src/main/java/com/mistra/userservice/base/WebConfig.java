package com.mistra.userservice.base;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: WangRui
 * @Date: 2018/11/30
 * Time: 16:53
 * Description:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private WebInterceptor webInterceptor;

    @Autowired
    private InterceptorIgnoreUrl interceptorIgnoreUrl;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor).addPathPatterns("/**").excludePathPatterns(interceptorIgnoreUrl.getUrl());
    }
}
