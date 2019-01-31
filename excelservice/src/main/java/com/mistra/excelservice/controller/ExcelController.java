package com.mistra.excelservice.controller;

import com.mistra.userservice.base.result.RequestResultBuilder;
import com.mistra.userservice.base.result.Result;
import com.mistra.excelservice.entity.ExcelEntity;
import com.mistra.excelservice.service.ExcelService;
import com.mistra.excelservice.util.ExcelImport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018/9/20
 * Time: 下午10:43
 * Description:
 */
@RestController
@Api("Excel导入Controller")
@RequestMapping("/excel")
public class ExcelController {

    Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    private ExcelService excelService;

    @ApiOperation("Excel导入")
    @ApiImplicitParam(name = "file", dataType = "MultipartFile", value = "Excel文件", required = true)
    @PostMapping(value = "/import")
    public Result ledgerImport(MultipartFile file) throws ParseException {
        try {
            List<ExcelEntity> list = ExcelImport.excelTransformationEntityList(ExcelEntity.class, file.getInputStream(), file.getOriginalFilename(), 1, 1);
            excelService.saveBatch(list);
            return RequestResultBuilder.success();
        } catch (IOException e) {
            logger.info(Arrays.toString(e.getStackTrace()));
            return RequestResultBuilder.failed("Excel解析错误！");
        }
    }

}
