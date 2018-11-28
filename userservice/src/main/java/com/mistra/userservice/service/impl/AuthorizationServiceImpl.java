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
 * Description:
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
     * 验证token是否有效
     *
     * @param token
     * @return
     */
    @Override
    public JWTVerifyStatus verification(String token) {
        if (StringUtils.isEmpty(token)) {
            logger.info("当前用户token为空，需要重新登录。");
            return JWTVerifyStatus.LOGIN;
        }
        DecodedJWT jwt = jwtVerifier.verify(token);
        Date refresh = jwt.getClaim(JWTConstant.TOKEN_REFRESH_EXPIRE_TIME).asDate();
        if (jwt.getExpiresAt().getTime() < System.currentTimeMillis()) {
            logger.info("access_token过期时间：" + jwt.getExpiresAt() + "  当前时间：" + new Date());
            logger.info("refresh_token过期时间：" + refresh + "  当前时间：" + new Date());
            if (refresh.getTime() < System.currentTimeMillis()) {
                logger.info("当前用户token已过期和refresh也过期，需要重新登录。");
                return JWTVerifyStatus.LOGIN;
            } else {
                logger.info("当前用户token已过期!refresh时间还在限制范围内，返回一个新token。token续期");
                return JWTVerifyStatus.CREATE_NEW;
            }
        }
        logger.info("token验证通过，正常访问！");
        return JWTVerifyStatus.SUCCESS;
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
     * 从请求拿到token
     *
     * @param httpServletRequest 从httpServletRequest中获取token
     * @return
     */
    @Override
    public String getToken(HttpServletRequest httpServletRequest) {
        return getTokenFormRequest(httpServletRequest);
    }

    private String getTokenFormRequest(HttpServletRequest requestContext) {
        String token = getTokenFromHeader(requestContext);
        if (StringUtils.isEmpty(token)) {
            token = getTokenFromQuery(requestContext);
        }
        return token;
    }

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

    private String getTokenFromQuery(HttpServletRequest request) {
        return request.getParameter("token");
    }
}
