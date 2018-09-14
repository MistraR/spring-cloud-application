package com.mistra.base.result;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午10:07
 * Description:
 */
public class Result {

    private boolean success;
    private String code;
    private String message;

    public Result(){

    }
    public Result(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
