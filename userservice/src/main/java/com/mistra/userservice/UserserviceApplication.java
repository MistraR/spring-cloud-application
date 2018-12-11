package com.mistra.userservice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mistra.base.JWT.JsonWebTokenConstant;
import com.mistra.base.JWT.JsonWwbTokenUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class UserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
    }

    @Bean
    public JsonWwbTokenUtil jWTUtil() {
        JsonWwbTokenUtil jwtUtil = new JsonWwbTokenUtil();
        jwtUtil.setAlgorithm(Algorithm.HMAC256(JsonWebTokenConstant.SECRET));
        jwtUtil.setJwtVerifier(JWT.require(jwtUtil.getAlgorithm()).withIssuer(JsonWebTokenConstant.ISSUER).build());
        return jwtUtil;
    }
}
