package com.mistra.base.result;

import lombok.Data;

import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018-09-21
 * Time: 下午3:32
 * Description:
 */
@Data
public class ListResult<T> extends Result{

    /**
     * 数据
     */
    private List<T> data;

}
