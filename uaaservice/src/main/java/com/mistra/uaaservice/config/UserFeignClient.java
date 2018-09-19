package com.mistra.uaaservice.config;

import com.mistra.base.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: WangRui
 * @Date: 2018-09-19
 * Time: 下午5:32
 * Description:
 */
@FeignClient(value = "user-service", configuration = FeignConfig.class)
@Component
public interface UserFeignClient {

    @GetMapping(value = "/user/test")
    Result test();
}
