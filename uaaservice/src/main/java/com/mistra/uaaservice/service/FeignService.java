package com.mistra.uaaservice.service;

import com.mistra.base.result.Result;
import com.mistra.uaaservice.config.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: WangRui
 * @Date: 2018-09-19
 * Time: 下午5:21
 * Description:
 */
@Service
public class FeignService {

    @Autowired
    private UserFeignClient userFeignClient;

    public Result test(){
        return userFeignClient.test();
    }
}
