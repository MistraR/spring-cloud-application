package com.mistra.userservice;

import com.mistra.userservice.web.CustomDispatcherServlet;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @Author: WangRui
 * @Date: 2018/12/20
 * Time: 18:35
 * Description:
 */
public class BaseApplication {

    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        //注解扫描上下文
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        //通过构造函数指定dispatcherServlet的上下文
        CustomDispatcherServlet servlet = new CustomDispatcherServlet(applicationContext);
        //用ServletRegistrationBean包装servlet
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(servlet, "/*");
        registrationBean.setLoadOnStartup(1);
        //指定urlmapping
        registrationBean.addUrlMappings("/rest/*");
        //指定name，如果不指定默认为dispatcherServlet
        registrationBean.setName("rest");

        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("50MB");
        factory.setMaxRequestSize("50MB");
        registrationBean.setMultipartConfig(factory.createMultipartConfig());

        return registrationBean;
    }
}
