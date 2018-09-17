package com.mistra.base.result;

import lombok.Data;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午10:16
 * Description:
 */
@Data
public class GenericResult<T> extends Result{

    private T data;

    public GenericResult(){

    }
}
