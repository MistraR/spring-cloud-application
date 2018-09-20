package com.mistra.excelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ExcelserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelserviceApplication.class, args);
    }
}
