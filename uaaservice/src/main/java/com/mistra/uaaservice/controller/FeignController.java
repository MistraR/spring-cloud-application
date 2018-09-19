package com.mistra.uaaservice.controller;

import com.mistra.base.result.Result;
import com.mistra.uaaservice.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: WangRui
 * @Date: 2018-09-19
 * Time: 下午5:21
 * Description:
 */
@RestController
public class FeignController {

    @Autowired
    private FeignService feignService;

    @GetMapping("/test")
    public Result test(){
        return feignService.test();
    }
}
