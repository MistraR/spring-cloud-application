package com.mistra.base.utils.enumutils;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019-05-09 11:20
 * @ Description:
 */
public interface IEnum {

    /**
     * 获取转换值
     *
     * @return 结果
     */
    default Object convertValue() {
        return null;
    }
}
