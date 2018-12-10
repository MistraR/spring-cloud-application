package com.mistra.excelservice.service;

import com.mistra.excelservice.entity.ExcelEntity;

import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018/9/20
 * Time: 下午10:44
 * Description:
 */
public interface ExcelService {

    /**
     * 批量保存
     *
     * @param list
     */
    void saveBatch(List<ExcelEntity> list);
}
