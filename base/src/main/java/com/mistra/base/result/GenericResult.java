package com.mistra.base.result;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午10:16
 * Description:
 */
public class GenericResult<T> extends Result{

    private T data;

    public GenericResult(){

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
