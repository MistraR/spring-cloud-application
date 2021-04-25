package com.mistra.redisservice;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2021/4/25 下午10:27
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@SpringBootTest(classes = RedisServiceApplication.class)
public class RedisServiceApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testPing() {
        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        String pong = connectionFactory.getConnection().ping();
        System.out.println("pong = " + pong);
    }

    @Test
    public void testString() {
        redisTemplate.opsForValue().set("username", "Mistra");
    }


}
