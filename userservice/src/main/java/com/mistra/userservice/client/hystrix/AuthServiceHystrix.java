package com.mistra.userservice.client.hystrix;


import com.mistra.userservice.client.AuthServiceClient;
import com.mistra.base.JWT.JWT;
import org.springframework.stereotype.Component;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/7/15/015
 * Describe: 熔断处理类
 */
//需要把熔断处理类以Bean的形式注入到Ioc容器中
@Component
public class AuthServiceHystrix implements AuthServiceClient {

    @Override
    public JWT getToken(String authorization, String type, String username, String password) {
        System.out.println("--------   uaa-service getToken hystrix   ---------");
        return null;
    }
}
