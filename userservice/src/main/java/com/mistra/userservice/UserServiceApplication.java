package com.mistra.userservice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import com.mistra.userservice.core.JWT.JsonWebTokenConstant;
import com.mistra.userservice.core.JWT.JsonWebTokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019-05-06 09:27
 * @ Description:
 */
@SpringBootApplication
@EnableEurekaClient
//@EnableDistributedTransaction
@ComponentScan(value = "com.mistra.base")
@ComponentScan(value = "com.mistra.userservice")
public class UserServiceApplication {

    @Value("${json-web-token.secret}")
    private String secret;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public JsonWebTokenUtils jsonWwbTokenUtil() {
        JsonWebTokenUtils jsonWebTokenUtils = new JsonWebTokenUtils();
        jsonWebTokenUtils.setAlgorithm(Algorithm.HMAC256(secret));
        jsonWebTokenUtils.setJwtVerifier(JWT.require(jsonWebTokenUtils.getAlgorithm()).withIssuer(JsonWebTokenConstant.ISSUER).build());
        return jsonWebTokenUtils;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
