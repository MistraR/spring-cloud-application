package com.mistra.uaaservice.controller;

import com.mistra.uaaservice.service.FeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: WangRui
 * @Date: 2018-09-19
 * Time: 下午5:21
 * Description:
 */
@Api(value = "服务调用测试", tags = {"服务调用测试"})
@RestController
public class FeignController {

    @Autowired
    private FeignService feignService;

    @ApiOperation("测试")
    @GetMapping("/test")
    public void test() {
        feignService.test();
    }

    @ApiOperation("发送邮件服务测试")
    @GetMapping("/sendMail")
    public void sendMail() {
        feignService.sendMail();
    }
}
