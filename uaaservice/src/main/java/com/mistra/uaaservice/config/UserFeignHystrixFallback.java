package com.mistra.uaaservice.config;

import org.springframework.stereotype.Component;

/**
 * @Author: WangRui
 * @Date: 2018-09-20
 * Time: 下午1:28
 * Description: 熔断器逻辑处理类
 */
@Component
public class UserFeignHystrixFallback implements UserFeignClient {

    @Override
    public void test() {
    }

    @Override
    public void distributedTransaction() {

    }
}
