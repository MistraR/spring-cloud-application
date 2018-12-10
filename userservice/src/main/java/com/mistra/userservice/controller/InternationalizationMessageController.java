package com.mistra.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @Author: WangRui
 * @Date: 2018/12/10
 * Time: 21:46
 * Description: 国际化信息验证controller
 */
@RequestMapping("/iim")
public class InternationalizationMessageController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {
        String result = "";
        Locale locale = LocaleContextHolder.getLocale();
        result = messageSource.getMessage("100001", null, locale);
        return result;
    }
}
