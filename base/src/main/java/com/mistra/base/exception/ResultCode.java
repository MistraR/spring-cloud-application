package com.mistra.base.exception;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午11:05
 * Description:
 */
public enum ResultCode {
    /**
     *
     */
    SUCCESS(1, "SUCCESS"),
    PARAMETER_ERROR(400, "参数验证错误"),
    UNAUTHORIZED(401, "无访问权限"),
    FORBIDDEN_ERROR(403, "拒绝访问"),
    METHOD_NOT_ALLOWED(405, "方法不允许访问"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持当前媒体类型"),
    UNKNOWN_ERROR(500, "未知异常"),
    SERVICE_ERROR(5001, "业务逻辑错误"),
    DATABASE_ERROR(5002, "数据库操作异常"),
    LOGIN_EXPIRED_ERROR(5003, "用户登录过期"),
    TOKEN_REFRESH_ERROR(5004, "TOKEN刷新失败");

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static String getMessage(int i) {
        for (ResultCode e : values()) {
            if (e.getCode() == i) {
                return e.getMessage();
            }
        }
        return null;
    }

    public static Integer getCode(String message) {
        for (ResultCode e : values()) {
            if (e.getMessage().equals(message)) {
                return e.getCode();
            }
        }
        return null;
    }

    public static ResultCode valueOf(int code) {
        for (ResultCode value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        return null;
    }
}
