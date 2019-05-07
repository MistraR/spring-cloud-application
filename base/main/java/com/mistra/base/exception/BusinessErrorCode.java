package com.mistra.base.exception;

/**
 * @author : WangRui
 * Time : 2019-05-05 17:30
 * Description:
 */
public class BusinessErrorCode {
    /**
     * 请求成功!
     */
    public static final int OK = 0;
    /**
     * 请求失败!
     */
    public static final int FAIL = -1;
    /**
     * 系统错误!
     */
    public static final int SYSTEM_ERROR = -2;

    /**
     * 请求参数为空!
     */
    public static final int REQUEST_PARAM_IS_EMPTY = 100001;
    /**
     * 请求参数错误!
     */
    public static final int REQUEST_PARAM_ERROR = 100002;
    /**
     * 请求地址不存在!
     */
    public static final int REQUEST_NO_HANDLER_FOUND = 100003;
    /**
     * 服务器繁忙，请稍后重试
     */
    public static final int SERVER_BUSY_ERROR = 100104;
    /**
     * 请登录!
     */
    public static final int TO_LOGIN = 200000;
    /**
     * 密码错误!
     */
    public static final int USER_LOGIN_PWD_ERROR_FAIL = 100204;
    /**
     * 登录过期，请重新登录
     */
    public static final int LOGIN_EXPIRE = 200008;
    /**
     * 该账号在别的设备登录！
     */
    public static final int LOGIN_OTHER = 200012;
    /**
     * 邮箱已存在
     */
    public static final int EMAIL_EXIST = 100352;

    /**
     * Excel导入
     */
    public static final int EXCEL_IMPORT_ERROR = 100401;
    /**
     * 上传文件格式不支持！
     */
    public static final int FILE_NOT_SUPPORT = 100402;
    /**
     * 上传文件解析错误
     */
    public static final int FILE_PARSING_ERROR = 100403;
}
