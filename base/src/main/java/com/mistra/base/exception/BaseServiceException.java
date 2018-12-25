package com.mistra.base.exception;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午11:05
 * Description:
 */
public class BaseServiceException extends RuntimeException {

    private final ResultCode resultCode;

    public BaseServiceException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public BaseServiceException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

}
