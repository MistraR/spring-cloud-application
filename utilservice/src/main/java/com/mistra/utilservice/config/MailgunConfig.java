package com.mistra.utilservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: WangRui
 * @Date: 2018-10-16
 * Time: 下午3:45
 * Description:
 */
@Configuration
public class MailgunConfig {

    /**
     * mailgun配置参数
     */
    @Autowired
    private MailgunConfigProperties mailgunConfigProperties;

    @Bean
    public net.sargue.mailgun.Configuration mailgunConfiguration(){
        net.sargue.mailgun.Configuration configuration = new net.sargue.mailgun.Configuration()
                .from(mailgunConfigProperties.getFrom(), mailgunConfigProperties.getFromAddress())
                .apiKey(mailgunConfigProperties.getApiKey())
                .domain(mailgunConfigProperties.getDomain());
        return configuration;
    }
}
