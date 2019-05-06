package com.mistra.userservice.base.JWT;

/**
 * @ Author: WangRui
 * @ Date: 2018-09-18
 * Time: 下午3:48
 * Description: JWT Token 常量
 */
public class JsonWebTokenConstant {

    /**
     * token里面用户id标记
     */
    public static final String TOKEN_USER_ID_FLAG = "u";
    /**
     * token里面token版本标记
     */
    public static final String TOKEN_VERSION_ID_FLAG = "v";
    /**
     * refresh_token 时间标记
     */
    public static final String REFRESH_TOKEN_FLAG = "r";

    /**
     * header里面用户token标记
     */
    public static final String RESPONSE_HEADER_USER_TOKEN_FLAG = "new_user_token";

    /**
     * redis token 标记后缀
     */
    public static final String REDIS_TOKEN_SUFFIX = "_token";

    /**
     * redis token 保存时间-秒
     */
    public static final int REDIS_TOKEN_EXPIRE = 30;

    /**
     * 近期token刷新时间间隔  毫秒
     */
    public static final int LATEST_REFRESH_TIME = 30000;

    /**
     * 签发人
     */
    public static final String ISSUER = "Mistra";

    /**
     * source
     */
    public static final String SOURCE = "API";

    /**
     * 登录版本标签
     */
    public static final int LOGIN_VERSION_TAG = 1;

    /**
     * token版本标签
     */
    public static final int TOKEN_VERSION_TAG = 2;

    /**
     * version 二进制初始值
     */
    public static final String VERSION_INITIAL_VALUE = "00000000000001";

}
