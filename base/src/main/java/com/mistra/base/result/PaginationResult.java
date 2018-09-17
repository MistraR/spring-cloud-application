package com.mistra.base.result;

import lombok.Data;

import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午10:19
 * Description:
 */
@Data
public class PaginationResult<T> extends GenericResult<List<T>>{

    private long totalData;

    private long totalPageNumber;

    private int currentPageNumber;

    private int pageSize;

    private List<T> data;

    public PaginationResult(){

    }
}
