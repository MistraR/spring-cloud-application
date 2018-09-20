package com.mistra.base.exception;

/**
 * @author: laoli
 * @date: 2017-12-29 21:52
 * @Description: 结果对象
 */
public class AjaxResult<T> {
    private String msg;
    private T data;
    private Integer code;

    public AjaxResult() {
        this.msg = ResultCode.SUCCESS.getMsg();
        this.code = ResultCode.SUCCESS.getCode();
        this.data = null;
    }

    public AjaxResult(T data) {
        this.msg = ResultCode.SUCCESS.getMsg();
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
    public static <T> AjaxResult<T> success(T data) {
        return new AjaxResult<>(data);
    }

    public static <T> AjaxResult<T> success() {
        return new AjaxResult<>();
    }



    /**
     * 业务错误
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> AjaxResult<T> serviceError(String msg) {
        AjaxResult<T> result = new AjaxResult<>();
        result.setCode(ResultCode.SERVICE_ERROR.getCode());
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static <T> AjaxResult<T> parameterError(String msg) {
        AjaxResult<T> result = new AjaxResult<>();
        result.setCode(ResultCode.PARAMETER_ERROR.getCode());
        if (msg == null) {
            result.setMsg(ResultCode.PARAMETER_ERROR.getMsg());
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
    public static <T> AjaxResult<T> error(ResultCode code) {
        AjaxResult<T> result = new AjaxResult<>();
        result.setCode(code.getCode());
        result.setMsg(code.getMsg());
        result.setData(null);
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public AjaxResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public AjaxResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public AjaxResult setCode(Integer code) {
        this.code = code;
        return this;
    }
}
