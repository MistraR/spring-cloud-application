package com.mistra.userservice.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mistra.base.JWT.JWTConstant;
import com.mistra.base.JWT.JWTVerifyStatus;
import com.mistra.userservice.service.AuthorizationService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 上午11:19
 * Description: token设计，token本身的过期时间作为用户是否重新登录的判断条件，在负载里放入一个刷新token的过期时间，这个时间一旦过期就会抛异常
 * 只要当前时间大于这个时间，而且token还没有过期，就重新生成一个token返回，不需要用户再登录
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private Logger logger = LoggerFactory.getLogger(AuthorizationServiceImpl.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.source}")
    private String source;

    private Algorithm algorithm;

    private JWTVerifier jwtVerifier;

    /**
     * 服务器加载Servlet的时候运行，并且只会被服务器执行一次
     */
    @PostConstruct
    private void init() {
        algorithm = Algorithm.HMAC256(secret);
        jwtVerifier = JWT.require(algorithm).withIssuer(issuer).build();
    }

    /**
     * 生成token
     *
     * @param userId 把userId编码到token负载中   同时把刷新token操作的过期时间也编码到负载中
     * @return
     */
    @Override
    public String generateToken(String userId) {
        //过期时间
        Date expire = Date.from(LocalDateTime.now().plusMinutes(JWTConstant.OVERDUE_TIME).atZone(ZoneId.systemDefault()).toInstant());
        //token可刷新过期时间
        Date refresh = Date.from(LocalDateTime.now().plusMinutes(JWTConstant.REFRESH_EXPIRE_TIME).atZone(ZoneId.systemDefault()).toInstant());
        return JWT.create()
                .withClaim(JWTConstant.HEADER_USER_ID_FLAG, userId)
                .withClaim(JWTConstant.TOKEN_REFRESH_EXPIRE_TIME, refresh)
                .withClaim("source", source)
                .withIssuer(issuer)
                .withExpiresAt(expire)
                .sign(algorithm);
    }

    /**
     * 验证token，返回状态
     *
     * @param token
     * @return
     */
    @Override
    public JWTVerifyStatus verification(String token) {
        if (StringUtils.isEmpty(token)) {
            logger.info("当前用户token为空，搞什么搞，需要重新登录！");
            return JWTVerifyStatus.LOGIN;
        }
        try {
            DecodedJWT jwt = jwtVerifier.verify(token);
            Date refresh = jwt.getClaim(JWTConstant.TOKEN_REFRESH_EXPIRE_TIME).asDate();
            String userId = jwt.getClaim(JWTConstant.HEADER_USER_ID_FLAG).asString();
            logger.info("当前用户id:{},access_token过期时间:{}, refresh_token时间:{}, 当前时间:{}!", userId, jwt.getExpiresAt(), refresh, LocalDateTime.now());
            if (refresh.getTime() > System.currentTimeMillis()) {
                logger.info("返回一个新token。token续期！");
                return JWTVerifyStatus.CREATE_NEW;
            }
            return JWTVerifyStatus.SUCCESS;
        } catch (Exception e) {
            logger.info("token验证失败！");
            return JWTVerifyStatus.LOGIN;
        }
    }

    /**
     * 从token中解析出userId
     *
     * @param token
     * @return
     */
    @Override
    public String parseTokenGetUserId(String token) {
        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getClaim(JWTConstant.HEADER_USER_ID_FLAG).asString();
    }

    /**
     * 从HttpServletRequest拿到token
     *
     * @param httpServletRequest 从httpServletRequest中获取token
     * @return
     */
    @Override
    public String getToken(HttpServletRequest httpServletRequest) {
        String token = getTokenFromHeader(httpServletRequest);
        if (StringUtils.isEmpty(token)) {
            token = getTokenFromQuery(httpServletRequest);
        }
        return token;
    }

    /**
     * 从请求头获取
     *
     * @param request
     * @return
     */
    private String getTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Token");
        if (StringUtils.isEmpty(header)) {
            return null;
        }
        String[] arr = header.split(" ");
        if (arr.length != JWTConstant.TOKEN_LENGTH) {
            return null;
        }
        if (!JWTConstant.TOKEN_HEAD.equals(arr[0])) {
            return null;
        }
        return arr[1];
    }

    /**
     * 从携带参数获取
     *
     * @param request
     * @return
     */
    private String getTokenFromQuery(HttpServletRequest request) {
        return request.getParameter("token");
    }
}
