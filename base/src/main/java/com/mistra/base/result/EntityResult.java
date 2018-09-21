package com.mistra.base.result;

import lombok.Data;

/**
 * @Author: WangRui
 * @Date: 2018-09-21
 * Time: 下午2:52
 * Description:
 */
@Data
public class EntityResult<T> extends Result{

    /**
     * 数据
     */
    private T data;

}
