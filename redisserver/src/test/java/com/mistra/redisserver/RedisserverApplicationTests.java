package com.mistra.redisserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisserverApplicationTests {

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

    @Test
    void contextLoads() {
    }

}
