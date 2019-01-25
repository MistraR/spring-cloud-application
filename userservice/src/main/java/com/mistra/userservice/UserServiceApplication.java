package com.mistra.userservice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mistra.userservice.base.JWT.JsonWebTokenConstant;
import com.mistra.userservice.base.JWT.JsonWwbTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * @author Mistra
 */
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public JsonWwbTokenUtil jWTUtil() {
        JsonWwbTokenUtil jwtUtil = new JsonWwbTokenUtil();
        jwtUtil.setAlgorithm(Algorithm.HMAC256(JsonWebTokenConstant.SECRET));
        jwtUtil.setJwtVerifier(JWT.require(jwtUtil.getAlgorithm()).withIssuer(JsonWebTokenConstant.ISSUER).build());
        return jwtUtil;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
