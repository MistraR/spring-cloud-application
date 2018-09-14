package com.mistra.base.result;

import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午4:26
 * Description:
 */
public class Results {

    public Results(){

    }

    public static Result success() {
        Result result = new Result();
        result.setSuccess(true);
        return result;
    }

    public static Result failed(String code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> GenericResult<T> successGeneric(T data) {
        GenericResult<T> result = new GenericResult();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> GenericResult<T> failedGeneric(String code, String message) {
        GenericResult<T> result = new GenericResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> PaginationResult<T> successPaged(List<T> data, long total) {
        PaginationResult<T> result = new PaginationResult();
        result.setData(data);
        result.setSuccess(true);
        result.setTotalPageNumber(total);
        return result;
    }

    public static <T> PaginationResult<T> successPaged(List<T> data, long total, int pageIndex, int pageSize) {
        PaginationResult<T> result = new PaginationResult();
        result.setSuccess(true);
        result.setData(data);
        result.setCurrentPageNumber(pageIndex);
        result.setPageSize(pageSize);
        result.setTotalPageNumber(total);
        return result;
    }
}
