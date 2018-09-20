package com.mistra.base.exception;

/**
 * 结果码
 *
 * @author lj
 */

public enum ResultCode {
    SUCCESS(1, "SUCCESS"),
    PARAMETER_ERROR(400, "参数异常"),
    UNAUTHORIZED(401, "无访问权限"),
    FORBIDDEN_ERROR(403, "拒绝访问"),
    METHOD_NOT_ALLOWED(405, "方法不允许访问"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持当前媒体类型"),
    UNKNOWN_ERROR(500, "未知异常"),
    SERVICE_ERROR(5001, "业务逻辑错误"),
    DATABASE_ERROR(5002, "数据库操作异常"),
    LOGIN_EXPIRED_ERROR(5003, "用户登录过期"),
    TOKEN_REFRESH_ERROR(5004, "TOKEN刷新失败");

    private Integer code;
    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
