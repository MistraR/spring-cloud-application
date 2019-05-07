package com.mistra.excelservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mistra.base.exception.BusinessErrorCode;
import com.mistra.base.exception.BusinessException;
import com.mistra.excelservice.controller.ExcelController;
import com.mistra.excelservice.dao.ExcelMapper;
import com.mistra.excelservice.entity.ExcelEntity;
import com.mistra.excelservice.service.ExcelService;
import com.mistra.excelservice.util.ExcelImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @ Author: WangRui
 * @ Date: 2018/9/20
 * Time: 下午10:44
 * Description:
 */
@Service
public class ExcelServiceImpl extends ServiceImpl<ExcelMapper, ExcelEntity> implements ExcelService {

    private Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    private ExcelMapper excelMapper;

    @Override
    public void saveBatch(MultipartFile file) {
        try {
            List<ExcelEntity> list = ExcelImport.excelTransformationEntityList(ExcelEntity.class, file.getInputStream(), file.getOriginalFilename(), 1, 1);
            this.saveBatch(list);
        } catch (ParseException p) {
            logger.info("ExcelController.ledgerImport() fail");
            throw new BusinessException(BusinessErrorCode.FILE_PARSING_ERROR);
        } catch (IOException e) {
            logger.info("ExcelController.ledgerImport() fail");
            throw new BusinessException(BusinessErrorCode.EXCEL_IMPORT_ERROR);
        }
    }
}
