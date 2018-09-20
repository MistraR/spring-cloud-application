package com.mistra.base.exception;


/**
 * @author lijun
 * @date 2018-04-02.
 * @descritpion 业务基础异常，所有业务异常继承这个异常
 */
public class BaseServiceException extends RuntimeException {
    private ResultCode resultCode;

    public BaseServiceException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public BaseServiceException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
