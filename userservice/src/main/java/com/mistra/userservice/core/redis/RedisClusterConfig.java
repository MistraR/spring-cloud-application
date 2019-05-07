package com.mistra.userservice.core.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019-05-06 14:09
 * @ Description:
 */
@Configuration
@ConditionalOnClass(RedisClusterConfig.class)
@EnableConfigurationProperties(RedisClusterProperties.class)
public class RedisClusterConfig {

    Logger logger = LoggerFactory.getLogger(RedisClusterConfig.class);

    @Resource
    private RedisClusterProperties redisClusterProperties;

    private static JedisPool jedisPool;

    @Bean
    public JedisCommands redisCluster() {
        if (redisClusterProperties.isCluster()) {

            Set<HostAndPort> nodes = new HashSet<>();
            for (String node : redisClusterProperties.getNodes()) {
                String[] parts = StringUtils.split(node, ":");
                logger.info("redisInit cluster=true node={}", node);
                nodes.add(new HostAndPort(parts[0], Integer.valueOf(parts[1])));
            }
            JedisCluster j = new JedisCluster(nodes, 5000, 1000);
            j.getClusterNodes().forEach((k, v) -> {
                logger.info("redisInit {}", k);
            });
            return j;
        }

        String node = redisClusterProperties.getNodes().get(0);
        logger.info("redisInit cluster=false node={}", node);
        String[] parts = StringUtils.split(node, ":");
        //redis.clients.jedis.Jedis jedis = new Jedis(parts[0], Integer.parseInt(parts[1]));

        if (jedisPool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(1000);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(100);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；单位毫秒
            //小于零:阻塞不确定的时间,  默认-1
            config.setMaxWaitMillis(1000 * 100);
            //在borrow(引入)一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(false);
            config.setTestWhileIdle(true);
            //return 一个jedis实例给pool时，是否检查连接可用性（ping()）
            config.setTestOnReturn(false);
            //connectionTimeout 连接超时（默认2000ms）
            //soTimeout 响应超时（默认2000ms）
            jedisPool = new JedisPool(config, parts[0], Integer.parseInt(parts[1]), 2000, null);
        }
        return new JedisWapper(jedisPool);
    }

    public static void closeRedis() {
        try {
            if (jedisPool != null) {
                jedisPool.close();
            }
        } catch (Exception e) {

        }
    }
}
