package com.mistra.base.result;

import lombok.Data;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午10:07
 * Description:
 */
@Data
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

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
