package com.mistra.routeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Author: WangRui
 * @Time: 2018/9/19/003
 * Describe:
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
public class RouteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RouteServiceApplication.class, args);
    }

//    @Bean
//    public JsonWwbTokenUtil jWTUtil() {
//        JsonWwbTokenUtil jwtUtil = new JsonWwbTokenUtil();
//        jwtUtil.setAlgorithm(Algorithm.HMAC256(JsonWebTokenConstant.SECRET));
//        jwtUtil.setJwtVerifier(JWT.require(jwtUtil.getAlgorithm()).withIssuer(JsonWebTokenConstant.ISSUER).build());
//        return jwtUtil;
//    }
}
