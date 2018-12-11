package com.mistra.base.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Author: WangRui
 * @Date: 2018/11/30
 * Time: 15:00
 * Description: JWT工具类
 */
@Data
public class JsonWwbTokenUtil {

    private Logger logger = LoggerFactory.getLogger(JsonWwbTokenUtil.class);

    private Algorithm algorithm;

    private JWTVerifier jwtVerifier;

    /**
     * 生成token
     *
     * @param userId 把userId编码到token负载中，同时把刷新token操作的过期时间也编码到负载中
     * @return
     */
    public String generateToken(String userId) {
        //过期时间
        Date expire = Date.from(LocalDateTime.now().plusMinutes(JsonWebTokenConstant.OVERDUE_TIME).atZone(ZoneId.systemDefault()).toInstant());
        //token可刷新过期时间
        Date refresh = Date.from(LocalDateTime.now().plusMinutes(JsonWebTokenConstant.REFRESH_EXPIRE_TIME).atZone(ZoneId.systemDefault()).toInstant());
        return JWT.create()
                .withClaim(JsonWebTokenConstant.HEADER_USER_ID_FLAG, userId)
                .withClaim(JsonWebTokenConstant.TOKEN_REFRESH_EXPIRE_TIME, refresh)
                .withClaim("source", JsonWebTokenConstant.SOURCE)
                .withIssuer(JsonWebTokenConstant.ISSUER)
                .withExpiresAt(expire)
                .sign(algorithm);
    }

    /**
     * 验证token，返回状态
     *
     * @param token
     * @return
     */
    public JsonWwbTokenVerifyStatus verification(String token) {
        if (StringUtils.isEmpty(token)) {
            logger.info("当前用户token为空，搞什么搞，需要重新登录！");
            return JsonWwbTokenVerifyStatus.LOGIN;
        }
        try {
            DecodedJWT jwt = jwtVerifier.verify(token);
            Date refresh = jwt.getClaim(JsonWebTokenConstant.TOKEN_REFRESH_EXPIRE_TIME).asDate();
            String userId = jwt.getClaim(JsonWebTokenConstant.HEADER_USER_ID_FLAG).asString();
            logger.info("当前用户id:{},access_token过期时间:{}, refresh_token时间:{}, 当前时间:{}!", userId, jwt.getExpiresAt(), refresh, LocalDateTime.now());
            if (refresh.getTime() < System.currentTimeMillis()) {
                logger.info("返回一个新token。token续期！");
                return JsonWwbTokenVerifyStatus.CREATE_NEW;
            }
            return JsonWwbTokenVerifyStatus.SUCCESS;
        } catch (Exception e) {
            logger.info("token验证失败！");
            return JsonWwbTokenVerifyStatus.LOGIN;
        }
    }

    /**
     * 从token中解析出userId
     *
     * @param token
     * @return
     */
    public String parseTokenGetUserId(String token) {
        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getClaim(JsonWebTokenConstant.HEADER_USER_ID_FLAG).asString();
    }

    /**
     * 从HttpServletRequest拿到token
     *
     * @param httpServletRequest 从httpServletRequest中获取token
     * @return
     */
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
        if (arr.length != JsonWebTokenConstant.TOKEN_LENGTH) {
            return null;
        }
        if (!JsonWebTokenConstant.TOKEN_HEAD.equals(arr[0])) {
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
