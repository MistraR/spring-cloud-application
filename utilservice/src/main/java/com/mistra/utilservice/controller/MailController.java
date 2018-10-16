package com.mistra.utilservice.controller;

import com.mistra.base.result.Result;
import com.mistra.utilservice.dto.MailDTO;
import com.mistra.utilservice.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: WangRui
 * @Date: 2018/10/14
 * Time: 下午10:34
 * Description:
 */
@Api(value = "邮件服务", tags = "{邮件服务}")
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/send")
    @ApiOperation("发送邮件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mailDTO", dataType = "MailDTO", required = true)
    })
    public Result sendMail(@RequestBody MailDTO mailDTO) {
        return mailService.sendMail(mailDTO);
    }
}
