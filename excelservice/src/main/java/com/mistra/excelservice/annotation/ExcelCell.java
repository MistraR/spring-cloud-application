package com.mistra.excelservice.annotation;

import java.lang.annotation.*;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午6:23
 * Description:
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelCell {

    /**
     * 列标记
     */
    int index() default 0;

    /**
     * Cell数据类型
     */
    ExcelCellType value();
}
