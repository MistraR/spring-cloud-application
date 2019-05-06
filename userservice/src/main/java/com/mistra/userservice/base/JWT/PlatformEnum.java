package com.mistra.userservice.base.JWT;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019-05-06 09:27
 * @ Description:
 */
public enum PlatformEnum {

    /**
     * 安卓
     */
    ANDROID(1, "android"),
    /**
     * IOS
     */
    IOS(2, "iphone"),
    /**
     * H5
     */
    H5(3, "w");

    private Integer code;
    private String message;

    PlatformEnum(Integer code, String message) {
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
