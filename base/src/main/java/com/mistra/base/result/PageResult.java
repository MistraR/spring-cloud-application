package com.mistra.base.result;

import lombok.Data;

import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018-09-21
 * Time: 下午2:52
 * Description:
 */
@Data
public class PageResult<T> extends Result{

    /**
     * 数据总行数
     */
    private long totalData;

    /**
     * 总页数
     */
    private long totalPageNumber;

    /**
     * 当前页
     */
    private int currentPageNumber;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 数据
     */
    private List<T> data;
}
