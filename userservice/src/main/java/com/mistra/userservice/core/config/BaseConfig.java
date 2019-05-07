package com.mistra.userservice.core.config;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午6:23
 * Description:
 */
@Configuration
public class BaseConfig {

    @Value("${pagehelper.offsetAsPageNum}")
    private String offsetAsPageNum;
    @Value("${pagehelper.rowBoundsWithCount}")
    private String rowBoundsWithCount;
    @Value("${pagehelper.reasonable}")
    private String reasonable;

    /**
     * 注册MyBatis分页插件PageHelper  使用MyBatis时才用
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", offsetAsPageNum);
        p.setProperty("rowBoundsWithCount", rowBoundsWithCount);
        p.setProperty("reasonable", reasonable);
        pageHelper.setProperties(p);
        return pageHelper;
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
