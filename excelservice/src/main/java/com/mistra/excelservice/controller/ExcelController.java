package com.mistra.excelservice.controller;

import com.mistra.excelservice.entity.ExcelEntity;
import com.mistra.excelservice.service.ExcelService;
import com.mistra.excelservice.util.ExcelImport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018/9/20
 * Time: 下午10:43
 * Description:
 */
@RestController
@Api("Excel导入Controller")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @ApiOperation("Excel导入")
    @PostMapping(value = "/ledgerImport")
    public String ledgerImport(MultipartFile file) throws ParseException {
        try {
            List<ExcelEntity> list = ExcelImport.excelTransformationEntityList(ExcelEntity.class, file.getInputStream(), file.getOriginalFilename(), 1, 1);
            excelService.saveBatch(list);
            return "导入成功。";
        } catch (IOException e) {
            e.printStackTrace();
            return "导入失败,请联系系统管理员。";
        }
    }

}
