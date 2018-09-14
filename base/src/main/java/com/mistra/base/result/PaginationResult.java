package com.mistra.base.result;

import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午10:19
 * Description:
 */
public class PaginationResult<T> extends GenericResult<List<T>>{

    private long totalPageNumber;

    private int currentPageNumber;

    private int pageSize;

    private List<T> data;

    public PaginationResult(){

    }

    public long getTotalPageNumber() {
        return totalPageNumber;
    }

    public void setTotalPageNumber(long totalPageNumber) {
        this.totalPageNumber = totalPageNumber;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public List<T> getData() {
        return data;
    }

    @Override
    public void setData(List<T> data) {
        this.data = data;
    }
}
