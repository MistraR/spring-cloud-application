package com.mistra.base.exception;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午11:05
 * Description:
 */
public class ResponseResult<T> {
    private String msg;
    private T data;
    private Integer code;

    public ResponseResult() {
        this.msg = ResultCode.SUCCESS.getMessage();
        this.code = ResultCode.SUCCESS.getCode();
        this.data = null;
    }

    public ResponseResult(T data) {
        this.msg = ResultCode.SUCCESS.getMessage();
        this.code = ResultCode.SUCCESS.getCode();
        this.data = data;
    }

    /**
     * 成功返回
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(data);
    }

    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>();
    }



    /**
     * 业务错误
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> serviceError(String msg) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(ResultCode.SERVICE_ERROR.getCode());
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static <T> ResponseResult<T> parameterError(String msg) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(ResultCode.PARAMETER_ERROR.getCode());
        if (msg == null) {
            result.setMsg(ResultCode.PARAMETER_ERROR.getMessage());
        } else {
            result.setMsg(msg);
        }
        result.setData(null);
        return result;
    }

    /**
     * 返回错误
     *
     * @param code
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> error(ResultCode code) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(code.getCode());
        result.setMsg(code.getMessage());
        result.setData(null);
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public ResponseResult setCode(Integer code) {
        this.code = code;
        return this;
    }
}
