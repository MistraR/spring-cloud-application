package com.mistra.base.result;

import lombok.Data;

import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午4:26
 * Description:
 */
@Data
public class RequestResultBuilder {

    public static Result success() {
        Result result = new Result();
        result.setSuccess(true);
        return result;
    }

    public static Result failed(String message) {
        Result result = new Result();
        result.setMessage(message);
        return result;
    }

    public static <T> EntityResult<T> entityResult(T data) {
        EntityResult<T> result = new EntityResult<>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> EntityResult<T> entityResult(T data, String message) {
        EntityResult<T> result = new EntityResult<>();
        result.setSuccess(true);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    public static <T> ListResult<T> listResult(List<T> data) {
        ListResult<T> result = new ListResult<>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> ListResult<T> listResult(List<T> data, String message) {
        ListResult<T> result = new ListResult<>();
        result.setSuccess(true);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    public static <T> PageResult<T> pageResult(List<T> data, long totalData, long totalPageNumber, int currentPageNumber, int pageSize) {
        PageResult<T> result = new PageResult<>();
        result.setData(data);
        result.setSuccess(true);
        result.setTotalPageNumber(totalPageNumber);
        result.setTotalData(totalData);
        result.setCurrentPageNumber(currentPageNumber);
        result.setPageSize(pageSize);
        return result;
    }

    public static <T> PageResult<T> pageResult(List<T> data, String message,long totalData, long totalPageNumber, int currentPageNumber, int pageSize) {
        PageResult<T> result = new PageResult<>();
        result.setData(data);
        result.setSuccess(true);
        result.setTotalPageNumber(totalPageNumber);
        result.setTotalData(totalData);
        result.setCurrentPageNumber(currentPageNumber);
        result.setPageSize(pageSize);
        result.setMessage(message);
        return result;
    }

}
