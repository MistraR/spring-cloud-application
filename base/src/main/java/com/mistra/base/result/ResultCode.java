package com.mistra.base.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午4:41
 * Description:
 */
public enum ResultCode {

    /**
     * 请求成功
     */
    SUCCESS(200, "请求处理成功！"),

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
    INTERNAL_SERVER_ERROR(500,"服务器超负载或正停机维护，无法处理请求"),

    /**
     * 业务逻辑错误
     */
    SERVICE_ERROR(999,"业务逻辑错误");

    int code;
    String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @JsonValue
    public int value() {
        return code;
    }

    @JsonCreator
    public static ResultCode valueOf(int code) {
        for (ResultCode resultCode : values()) {
            if (resultCode.code == code) {
                return resultCode;
            }
        }
        throw new IllegalArgumentException("invalid code");
    }
}
