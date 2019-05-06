package com.mistra.userservice.base.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019-05-06 14:08
 * @ Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisClusterProperties {

    private boolean cluster;

    private List<String> nodes;
}
