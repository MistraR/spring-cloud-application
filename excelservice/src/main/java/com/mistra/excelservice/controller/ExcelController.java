package com.mistra.excelservice.controller;

import com.mistra.base.annotation.MistraResponseBody;
import com.mistra.excelservice.service.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ Author: WangRui
 * @ Date: 2018/9/20
 * Time: 下午10:43
 * Description:
 */
@RestController
@Api("Excel导入Controller")
@RequestMapping("/excel")
public class ExcelController {


    @Autowired
    private ExcelService excelService;

    @ApiOperation("Excel导入")
    @ApiImplicitParam(name = "file", dataType = "MultipartFile", value = "Excel文件", required = true)
    @PostMapping(value = "/import")
    @MistraResponseBody
    public void ledgerImport(MultipartFile file) {
        excelService.saveBatch(file);
    }

}
