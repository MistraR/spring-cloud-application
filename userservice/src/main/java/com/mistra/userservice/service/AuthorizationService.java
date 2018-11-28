package com.mistra.userservice.service;

import com.mistra.base.JWT.JWTVerifyStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午11:15
 * Description:
 */
public interface AuthorizationService {

    /**
     * 生成token
     *
     * @param userId 把userId编码到token负载中
     * @return
     */
    String generateToken(String userId);

    /**
     * 解析token
     *
     * @param token
     * @return 从token中解析出userId
     */
    String parseTokenGetUserId(String token);

    /**
     * 验证token是否有效
     *
     * @param token
     * @return
     */
    JWTVerifyStatus verification(String token);

    /**
     * 获取token
     *
     * @param httpServletRequest 从httpServletRequest中获取token
     * @return
     */
    String getToken(HttpServletRequest httpServletRequest);


}
