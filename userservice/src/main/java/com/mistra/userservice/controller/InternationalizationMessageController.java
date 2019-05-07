package com.mistra.userservice.controller;

import com.mistra.base.annotation.MistraResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @ Author: WangRui
 * @ Date: 2018/12/10
 * Time: 21:46
 * Description: 国际化信息验证controller
 */
@RequestMapping("/iim")
@RestController
public class InternationalizationMessageController {

    @Autowired
    private MessageSource messageSource;

    /**
     * 请求头添加 Accept-Language - en-US|zh-CN
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/test")
    @MistraResponseBody
    public String test(HttpServletRequest request, HttpServletResponse response) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("100001", null, locale);
    }
}
