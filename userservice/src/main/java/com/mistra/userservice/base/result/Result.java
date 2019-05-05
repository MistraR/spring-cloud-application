package com.mistra.userservice.base.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mistra.userservice.base.exception.SystemCodeMessage;
import lombok.Data;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午10:07
 * Description:
 */
@Data
public class Result {

    private int code = SystemCodeMessage.SUCCESS.getCode();
    private String message = SystemCodeMessage.SUCCESS.getMessage();
    private Object data;

    public Result() {
        super();
    }

    public Result(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Result(Object data) {
        this.data = data;
    }

    public Result(String message, Object data) {
        this.data = data;
        this.message = message;
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public Result(String message) {
        this.message = message;
    }

    private static SerializerFeature[] features = {SerializerFeature.DisableCircularReferenceDetect};

    public String toJson() {
        return JSON.toJSONString(this, features);
    }

    public static Result ok(Object data) {
        return new Result(data);
    }

    public static Result ok() {
        return new Result();
    }

    public static Result fail(int code, String message) {
        return new Result(code, message);
    }

    public static Result fail(String message) {
        return new Result(SystemCodeMessage.FAIL.getCode(), message);
    }


}
