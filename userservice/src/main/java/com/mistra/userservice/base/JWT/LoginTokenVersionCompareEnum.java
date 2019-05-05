package com.mistra.userservice.base.JWT;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019-05-05 23:31
 * @ Description:
 */
public enum LoginTokenVersionCompareEnum {

    /**
     * 刷新版本相差1，login版本一致
     */
    TOKEN_DIFFERENCE_ONE(1, "刷新版本相差1，login版本一致"),
    /**
     * 刷新版本不一致,login版本一致
     */
    TOKEN_DIFFERENCE(2, "刷新版本不一致，login版本一致"),
    /**
     * 30秒内刷新过token
     */
    LATEST_REFRESH(5, "30秒内刷新过token"),
    /**
     * 都一致
     */
    CONSISTENT(3, "都一致"),
    /**
     * 都不一致,login版本不一致时刷新版本也肯定不一致
     */
    IN_CONSISTENT(4, "都不一致");

    private Integer code;
    private String message;

    LoginTokenVersionCompareEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
