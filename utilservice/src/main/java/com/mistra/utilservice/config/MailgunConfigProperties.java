package com.mistra.utilservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: WangRui
 * @Date: 2018-10-16
 * Time: 下午3:47
 * Description: Mailgun配置参数
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mailgun")
public class MailgunConfigProperties {

    private String domain;

    private String apiKey;

    private String from;

    private String fromAddress;

    private String mailgunResource;
}
