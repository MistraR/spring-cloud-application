package com.mistra.base.advice;

import com.alibaba.fastjson.JSON;
import com.mistra.base.annotation.MistraResponseBody;
import com.mistra.base.model.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Author: WangRui
 * @Date: 2019/1/25
 * Time: 10:49
 * Description: 拦截MistraResponseBody注解修饰的Controller方法，封装返回值
 */
@ControllerAdvice
public class MistraResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(MistraResponseBody.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (selectedConverterType == StringHttpMessageConverter.class) {
            return JSON.toJSONString(new Result(body));
        }
        return new Result(body);
    }
}
