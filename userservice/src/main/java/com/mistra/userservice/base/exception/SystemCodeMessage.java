package com.mistra.userservice.base.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午4:41
 * Description:
 */
public enum SystemCodeMessage {

    /**
     * 请求成功
     */
    SUCCESS(0, "请求处理成功！"),

    /**
     * 系统错误
     */
    FAIL(-1, "系统错误！"),

    /**
     * 请求报文存在语法错误或参数错误
     */
    BAD_REQUEST(400,"请求报文存在语法错误或参数错误"),

    /**
     * 权限认证失败
     */
    UNAUTHORIZED(401,"权限认证失败"),

    /**
     * 禁止访问
     */
    FORBIDDEN(403,"禁止访问"),

    /**
     * 服务器找不到请求的资源
     */
    NOT_FOUND(404,"服务器找不到请求的资源"),

    /**
     * 服务器超负载或正停机维护，无法处理请求
     */
    INTERNAL_SERVER_ERROR(500,"服务器超负载或正停机维护，无法处理请求");

    int code;
    String message;

    SystemCodeMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @JsonValue
    public int value() {
        return code;
    }

    @JsonCreator
    public static SystemCodeMessage valueOf(int code) {
        for (SystemCodeMessage systemCodeMessage : values()) {
            if (systemCodeMessage.code == code) {
                return systemCodeMessage;
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
