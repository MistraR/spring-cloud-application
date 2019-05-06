package com.mistra.userservice.base.JWT;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019-05-05 23:30
 * @ Description:
 */
@Component
@ConfigurationProperties(prefix = "json-web-token")
@Data
public class JsonWebTokenProperties {

    /**
     * 登录过期时间-分钟
     */
    private int accessTokenExpireTime;

    /**
     * 刷新token过期时间-分钟
     */
    private int refreshTokenExpireTime;

    /**
     * 密钥
     */
    private String secret;

    /**
     * login token version code中头部长度
     */
    private Integer loginTokenVersionCodeHeaderLength;

    /**
     * login token version code中token占用长度
     */
    private Integer loginTokenVersionCodeTokenLength;

    /**
     * 拦截器需要忽略的路径
     */
    List<String> ignoreUrl;
}
