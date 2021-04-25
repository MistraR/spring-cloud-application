package com.mistra.redisserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2021/4/25 下午9:54
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
public class JedisTest {

    Jedis jedis = null;

    @Before
    public void init() {
        jedis = new Jedis("localhost", 6379);
        // jedis.auth("123456");
        // 心跳检测是否连接成功
        String pong = jedis.ping();
        System.out.println("建立连接：pong = " + pong);

    }

    @Test
    public void test() {
        String select = jedis.select(3);
        System.out.println("select = " + select);

        String result = jedis.set("username", "Mistra");
        System.out.println("result = " + result);

        String username = jedis.get("username");
        System.out.println("username = " + username);

        String result2 = jedis.set("com.mistra:1", "Mistra");
        System.out.println("result2 = " + result2);

        String username2 = jedis.get("com.mistra:1");
        System.out.println("username2 = " + username2);
    }

    @Test
    public void keys() {
        jedis.select(3);
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
    }

    @After
    public void close() {
        if (jedis != null) {
            jedis.close();
            System.out.println("断开连接！");
        }
    }
}
