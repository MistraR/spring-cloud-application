package com.mistra.zipkinserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin.server.internal.EnableZipkinServer;

@SpringBootApplication
//开启ZipkinServer功能
@EnableZipkinServer
@EnableEurekaClient
public class ZipkinserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinserverApplication.class, args);
    }
}
