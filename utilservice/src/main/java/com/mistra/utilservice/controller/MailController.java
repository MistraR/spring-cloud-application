package com.mistra.utilservice.controller;

import com.mistra.utilservice.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: WangRui
 * @Date: 2018/10/14
 * Time: 下午10:34
 * Description:
 */
@Api("邮件服务")
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/send")
    @ApiOperation("发送邮件")
    public void sendMail(){
        mailService.sendMail();
    }
}
