package com.mistra.userservice.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mistra.base.constant.JWTConstant;
import com.mistra.base.date.TimeUtil;
import com.mistra.userservice.service.AuthorizationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
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
     * @param userId 把userId编码到token负载中
     * @return
     */
    @Override
    public String generateToken(String userId) {
        //过期时间
        Instant instant = LocalDateTime.now().plusHours(JWTConstant.OVERDUE_TIME).atZone(ZoneId.systemDefault()).toInstant();
        Date expire = Date.from(instant);
        return JWT.create()
                .withClaim(JWTConstant.HEADER_USER_ID_FLAG, userId)
                .withClaim("source", source)
                .withIssuer(issuer)
                .withExpiresAt(expire)
                .sign(algorithm);
    }

    /**
     * 从token中解析出userId
     *
     * @param token
     * @return
     */
    @Override
    public String parseToken(String token) {
        DecodedJWT jwt = jwtVerifier.verify(token);
        if (jwt.getExpiresAt().before(TimeUtil.getNowTime())) {
            return null;
        }
        return jwt.getClaim(JWTConstant.HEADER_USER_ID_FLAG).asString();
    }

    /**
     * 从请求拿到token
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
