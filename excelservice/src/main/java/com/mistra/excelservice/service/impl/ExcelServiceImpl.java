package com.mistra.excelservice.service.impl;

import com.mistra.excelservice.dao.ExcelMapper;
import com.mistra.excelservice.entity.ExcelEntity;
import com.mistra.excelservice.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018/9/20
 * Time: 下午10:44
 * Description:
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private ExcelMapper excelMapper;

    @Override
    public void saveBatch(List<ExcelEntity> list) {

    }
}
