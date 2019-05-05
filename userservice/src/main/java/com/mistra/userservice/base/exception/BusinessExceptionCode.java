package com.mistra.userservice.base.exception;

/**
 * @author : WangRui
 * Time : 2019-05-05 17:30
 * Description:
 */
public class BusinessExceptionCode {
    /**
     * 请求成功!
     */
    public static final int OK = 0;
    /**
     * 请求失败!
     */
    public static final int FAIL = -1;

    /**
     * 请求参数为空!
     */
    public static final int REQUEST_PARAM_IS_EMPTY = 100001;
    /**
     * 请求参数错误!
     */
    public static final int REQUEST_PARAM_ERROR = 100002;
    /**
     * 密码错误!
     */
    public static final int USER_LOGIN_PWD_ERROR_FAIL = 100204;
}
