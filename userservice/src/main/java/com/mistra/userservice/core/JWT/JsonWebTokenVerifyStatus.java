package com.mistra.userservice.core.JWT;

/**
 * @ Author: WangRui
 * @ Date: 2018/11/28
 * Time: 16:56
 * Description:
 */
public enum JsonWebTokenVerifyStatus {
    /**
     * 验证成功，可以继续访问
     */
    SUCCESS(1001, "Token authentication successful！"),
    /**
     * 重新生成token
     */
    CREATE_NEW(1002, "Return a new token！Token renewal!"),
    /**
     * 在别处登录过
     */
    LOGIN_OTHER(1003, "Login other!"),
    /**
     * Token过期或者无效，需要重新登录
     */
    LOGIN(1004, "Token is invalid or overdue，To login!"),
    /**
     * token版本相差1
     */
    LATEST_REFRESH(1006, "Token differ one!"),;

    Integer code;
    String message;

    JsonWebTokenVerifyStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
