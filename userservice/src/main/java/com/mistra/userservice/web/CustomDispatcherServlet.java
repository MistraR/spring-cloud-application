package com.mistra.userservice.web;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @Author: WangRui
 * @Date: 2018/12/20
 * Time: 18:29
 * Description: 自定义的DispatcherServlet，复写父类方法，实现自己的需求
 */
public class CustomDispatcherServlet extends DispatcherServlet {

    private static final long serialVersionUID = 1L;

    private static final String ZH_CN = "zh";

    public CustomDispatcherServlet(AnnotationConfigWebApplicationContext applicationContext) {
        super(applicationContext);
    }

    /**
     * 根据请求头参数"lan"手动设置语言本地化对象
     * 可以选择用 Accept-Language,请求头带了此参数的话Spring会自动识别语言类型
     *
     * @param request
     * @return
     */
    @Override
    public LocaleContext buildLocaleContext(HttpServletRequest request) {
        String language = request.getHeader(RequestHeaderConstant.HEADER_LANGUAGE_FLAG);
        LocaleContext localeContext = new SimpleLocaleContext(new Locale("zh", "CN"));
        if (!StringUtils.isEmpty(language)) {
            return language.startsWith(ZH_CN) ? localeContext : new SimpleLocaleContext(new Locale("en", "US"));
        } else {
            return localeContext;
        }
    }
}
