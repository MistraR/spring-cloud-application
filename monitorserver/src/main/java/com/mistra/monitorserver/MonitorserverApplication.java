package com.mistra.monitorserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
//用于聚合多个Hystrix Dashboard
@EnableTurbine
@EnableEurekaClient
public class MonitorserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorserverApplication.class, args);
    }
}
