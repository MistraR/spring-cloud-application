package com.mistra.userservice.client;

import com.mistra.userservice.client.hystrix.AuthServiceHystrix;
import com.mistra.userservice.entity.JWT;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/7/15/015
 * Describe: 调用uaa-service服务来获取JWT
 */
@FeignClient(value = "uaa-service",fallback =AuthServiceHystrix.class )
public interface AuthServiceClient {

    @PostMapping(value = "/oauth/token")
    JWT getToken(@RequestHeader(value = "Authorization") String authorization, @RequestParam("grant_type") String type,
                 @RequestParam("username") String username, @RequestParam("password") String password);
}



