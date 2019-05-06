package com.mistra.userservice.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @Author: WangRui
 * @Date: 2019/1/25
 * Time: 11:48
 * Description:
 */
@Component
public class InternationalizationUtil {

    @Autowired
    private MessageSource messageSource;

    /**
     * 根据errorCode和本地化对象Local获取国际化提示信息
     *
     * @param errorCode
     * @return
     */
    public String i18n(int errorCode) {
        return i18n(String.valueOf(errorCode));
    }

    public String i18n(String errorCode) {
        return messageSource.getMessage(errorCode, null, errorCode, LocaleContextHolder.getLocale());
    }

    public String i18n(String errorCode, Object[] args) {
        return messageSource.getMessage(errorCode, args, LocaleContextHolder.getLocale());
    }
}
