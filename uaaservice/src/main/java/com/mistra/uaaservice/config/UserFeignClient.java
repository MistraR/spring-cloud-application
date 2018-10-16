package com.mistra.uaaservice.config;

import com.mistra.base.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: WangRui
 * @Date: 2018-09-19
 * Time: 下午5:32
 * Description:远程调用的服务ID，Feign配置。熔断器逻辑处理类
 */
@FeignClient(value = "user-service", configuration = FeignConfig.class,fallback = FeignHystrixFallback.class)
@Component
public interface UserFeignClient {

    /**
     * feign调用测试
     * @return
     */
    @GetMapping(value = "/user/test")
    Result test();
}
