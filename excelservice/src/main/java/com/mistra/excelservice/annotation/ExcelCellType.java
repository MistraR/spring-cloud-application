package com.mistra.excelservice.annotation;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午6:23
 * Description:
 */

public enum ExcelCellType {
    /**
     * 数值
     */
    CELL_TYPE_NUMERIC,

    /**
     * BigDecimal
     */
    CELL_TYPE_DECIMAL,

    /**
     * 字符串
     */
    CELL_TYPE_STRING,

    /**
     * 日期
     */
    CELL_TYPE_DATE,

    /**
     * boolean
     */
    CELL_TYPE_BOOLEAN,

    /**
     * 公式
     */
    CELL_TYPE_FORMULA,

    /**
     * 空
     */
    CELL_TYPE_BLANK,

    /**
     * 错误格式
     */
    CELL_TYPE_ERROR
}
