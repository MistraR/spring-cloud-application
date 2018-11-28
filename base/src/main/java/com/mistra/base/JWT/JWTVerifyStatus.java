package com.mistra.base.JWT;

/**
 * @Author: WangRui
 * @Date: 2018/11/28
 * Time: 16:56
 * Description:
 */
public enum JWTVerifyStatus {
    /**
     * 验证成功，可以继续访问
     */
    SUCCESS(1, "验证成功，可以继续访问"),
    /**
     * 重新生成token
     */
    CREATE_NEW(2, "重新生成token"),
    /**
     * 需要重新登录
     */
    LOGIN(3, "token无效，需要重新登录");

    Integer code;
    String message;

    JWTVerifyStatus(Integer code, String message) {
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
