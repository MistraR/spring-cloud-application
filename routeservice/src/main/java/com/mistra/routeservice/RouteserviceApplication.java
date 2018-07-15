package com.mistra.routeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
//开启Zuul
@EnableZuulProxy
public class RouteserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RouteserviceApplication.class, args);
    }
}
