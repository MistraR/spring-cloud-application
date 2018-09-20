package com.mistra.uaaservice.config;

import com.mistra.base.result.Result;
import com.mistra.base.result.ResultCode;
import com.mistra.base.result.Results;
import org.springframework.stereotype.Component;

/**
 * @Author: WangRui
 * @Date: 2018-09-20
 * Time: 下午1:28
 * Description: 熔断器逻辑处理类
 */
@Component
public class FeignHystrixFallback implements UserFeignClient{

    @Override
    public Result test() {
        return Results.failed(ResultCode.ERROR,"Feign Error!");
    }
}
