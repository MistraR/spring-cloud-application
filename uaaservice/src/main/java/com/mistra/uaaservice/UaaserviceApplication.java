package com.mistra.uaaservice;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrixDashboard//http://localhost:8002/hystrix
//注释掉分布式事物
//@EnableDistributedTransaction
public class UaaserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UaaserviceApplication.class, args);
    }
}
