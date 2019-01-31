package com.mistra.userservice.base.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: WangRui
 * @Date: 2019/1/31
 * Time: 11:11
 * Description:
 */
public enum BusinessCodeMessage {
    /**
     * 系统错误
     */
    SYSTEM_ERROR(-1, "系统错误！"),

    /**
     * 服务器超负载或正停机维护，无法处理请求
     */
    INTERNAL_SERVER_ERROR(500,"服务器超负载或正停机维护，无法处理请求");

    int code;
    String message;

    BusinessCodeMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @JsonValue
    public int value() {
        return code;
    }

    @JsonCreator
    public static BusinessCodeMessage valueOf(int code) {
        for (BusinessCodeMessage businessCodeMessage : values()) {
            if (businessCodeMessage.code == code) {
                return businessCodeMessage;
            }
        }
        throw new IllegalArgumentException("invalid code");
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
