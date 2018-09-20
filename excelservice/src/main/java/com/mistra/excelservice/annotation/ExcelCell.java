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
    int index() default 0;//列标记
    ExcelCellType value();//Cell类型
}
