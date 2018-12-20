package com.mistra.base.JWT;

/**
 * @Author: WangRui
 * @Date: 2018-09-18
 * Time: 下午3:48
 * Description: JWT Token 常量
 */
public class JsonWebTokenConstant {

    /**
     * header里面用户key值
     */
    public final static String HEADER_USER_ID_FLAG = "userId";

    /**
     * token过期时间
     */
    public final static String TOKEN_REFRESH_EXPIRE_TIME = "refreshExpire";

    /**
     * header里面用户token标记
     */
    public static final String RESPONSE_HEADER_USER_TOKEN_FLAG = "token";


    /**
     * token头
     */
    public final static String TOKEN_HEAD = "Mistra";

    /**
     * token分割后长度
     */
    public final static int TOKEN_LENGTH = 2;

    /**
     * access_token过期时间-分钟
     */
    public final static int OVERDUE_TIME = 60000;

    /**
     * refresh_token过期时间-分钟
     */
    public final static int REFRESH_EXPIRE_TIME = 30000;

    /**
     * 编码密钥
     */
    public final static String SECRET = "mistra.wang";

    /**
     * 作者
     */
    public final static String ISSUER = "MistraR";

    /**
     * source
     */
    public final static String SOURCE = "springCloud";

}
