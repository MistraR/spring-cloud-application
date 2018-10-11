package com.mistra.base.exception;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午11:05
 * Description:
 */
public class DataBaseRollBackException extends BaseServiceException {
    public DataBaseRollBackException() {
        super(ResultCode.DATABASE_ERROR);
    }
}
