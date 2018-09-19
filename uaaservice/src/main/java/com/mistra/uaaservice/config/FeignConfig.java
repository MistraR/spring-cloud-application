package com.mistra.uaaservice.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @Author: WangRui
 * @Date: 2018-09-19
 * Time: 下午5:25
 * Description: Feign 远程调用失败后会进行重试
 */
@Configuration
public class FeignConfig {
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100, SECONDS.toMillis(1), 5);
    }
}
