package com.mistra.userservice.controller;

import com.mistra.base.result.RequestResultBuilder;
import com.mistra.base.result.Result;
import com.mistra.userservice.vo.ParameterVerificationDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: WangRui
 * @Date: 2018-10-11
 * Time: 下午4:37
 * Description: 参数验证测试Controller
 */
@RestController
@RequestMapping("/paraVerify")
public class ParameterVerificationController {

    @GetMapping("/dto")
    @ApiOperation("实体类型参数验证")
    public Result dto(ParameterVerificationDTO parameterVerificationDTO){
        return RequestResultBuilder.success();
    }
}
