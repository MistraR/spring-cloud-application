package com.mistra.userservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//作为服务端向注册中心注册
@EnableEurekaClient
//开启Feign调用
@EnableFeignClients
//开启熔断器，当服务出现故障时，让调用这个服务的其他服务快速失败，防止线程阻塞导致线程资源耗尽，防止微服务系统中的雪崩效应
@EnableHystrix
//开启熔断器监控组件，实时查看荣孤单器的状况，如是否开启和关闭等等
@EnableHystrixDashboard
@EnableCircuitBreaker
@MapperScan("com.mistra.userservice.dao")
public class UserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
    }
}
