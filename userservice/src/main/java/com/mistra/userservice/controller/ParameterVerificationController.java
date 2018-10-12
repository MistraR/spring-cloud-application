package com.mistra.userservice.controller;

import com.mistra.base.result.RequestResultBuilder;
import com.mistra.base.result.Result;
import com.mistra.userservice.dto.ParameterVerificationDTO;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * @Author: WangRui
 * @Date: 2018-10-11
 * Time: 下午4:37
 * Description: 参数验证测试Controller
 */
@Api(value = "参数验证测试", tags = "{参数验证测试}")
@RestController
@RequestMapping("/paraVerify")
@Validated
public class ParameterVerificationController {

    @GetMapping("/dto")
    @ApiOperation("实体类类型参数验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parameterVerificationDTO", value = "参数验证DTO", dataType = "ParameterVerificationDTO", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功处理"),
            @ApiResponse(code = 400, message = "请求参数错误")
    })
    public Result dto(@RequestBody @Valid ParameterVerificationDTO parameterVerificationDTO) {
        return RequestResultBuilder.success();
    }

    @GetMapping("/{game}/{file}/{type}/{label}/common")
    @ApiOperation("路径类型参数验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "game", value = "游戏编码", paramType = "path", required = true, dataType = "String"),
            @ApiImplicitParam(name = "file", value = "文件名", paramType = "path", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型", paramType = "path", required = true, dataType = "String"),
            @ApiImplicitParam(name = "label", value = "父级标记", paramType = "path", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "String", defaultValue = "15")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求成功处理"),
            @ApiResponse(code = 400, message = "请求参数错误")
    })
    public Result common(@PathVariable String game, @PathVariable String file, @PathVariable String type, @PathVariable String label, @RequestParam(defaultValue = "1") @Min(1) String pageNum, @RequestParam(defaultValue = "15") @Min(1) String pageSize) {
        return RequestResultBuilder.success();
    }
}
