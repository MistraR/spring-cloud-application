package com.mistra.utilservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.dialect.SpringStandardDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

/**
 * @Author: WangRui
 * @Date: 2018-10-16
 * Time: 下午2:46
 * Description: 字符串模板解析的配置
 */
@Configuration
public class SpringTemplateEngineConfig {

    @Bean(name = "stringTemplateEngine")
    public SpringTemplateEngine springTemplateEngine() {
        //字符串模板引擎对象
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        //内置方言
        IDialect springStandardDialect = new SpringStandardDialect();
        springTemplateEngine.setDialect(springStandardDialect);
        //字符串解析器
        StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();
        //使用缓存
        stringTemplateResolver.setCacheable(true);
        stringTemplateResolver.setTemplateMode(TemplateMode.HTML);
        springTemplateEngine.setTemplateResolver(stringTemplateResolver);
        return springTemplateEngine;
    }
}
