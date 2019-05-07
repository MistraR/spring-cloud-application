package com.mistra.userservice.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mistra.userservice.core.JWT.*;
import com.mistra.base.exception.BusinessErrorCode;
import com.mistra.base.exception.BusinessException;
import com.mistra.base.utils.i18n.InternationalizationUtil;
import com.mistra.base.utils.HttpServletHelper;
import com.mistra.userservice.core.web.RequestHeaderConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.JedisCommands;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019-05-06 10:30
 * @ Description:
 */
@Component
public class BasedInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(BasedInterceptor.class);

    @Autowired
    private JsonWebTokenUtils jsonWebTokenUtils;

    @Autowired
    private InternationalizationUtil i18Util;

    @Autowired
    private JedisCommands jedisCommands;

    /**
     * preHandle()方法在业务处理器处理请求之前被调用
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  handler
     * @return boolean
     * @throws Exception Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = HttpServletHelper.getRequestIpAddr(request);
        String uri = request.getRequestURI().toLowerCase();
        logger.info("IP:{},uri:{}", ip, uri);

        String headerUserId = request.getHeader(RequestHeaderConstant.USER_ID);
        if (StringUtils.isBlank(headerUserId)) {
            this.toOut(response, BusinessErrorCode.TO_LOGIN);
            return false;
        }
        //验证Token
        JsonWebTokenVerifyStatus jsonWebTokenVerifyStatus = jsonWebTokenUtils.verification(request);
        if (jsonWebTokenVerifyStatus.equals(JsonWebTokenVerifyStatus.LOGIN)) {
            logger.debug("uid:{}--------------------------------------------,JsonWebTokenVerifyStatus.LOGIN", headerUserId);
            //登陆过期或登录异常，需要重新登录!
            throw new BusinessException(BusinessErrorCode.LOGIN_EXPIRE);
        } else if (jsonWebTokenVerifyStatus.equals(JsonWebTokenVerifyStatus.LOGIN_OTHER)) {
            logger.debug("uid:{}--------------------------------------------,JsonWebTokenVerifyStatus.LOGIN_OTHER", headerUserId);
            //该账号在别的设备登录！  提示信息
            this.toOut(response, BusinessErrorCode.LOGIN_OTHER);
            return false;
        } else if (jsonWebTokenVerifyStatus.equals(JsonWebTokenVerifyStatus.SUCCESS)) {
            //验证成功放行
            return true;
        } else if (jsonWebTokenVerifyStatus.equals(JsonWebTokenVerifyStatus.CREATE_NEW)) {
            String userId = CurrentUserSession.getUserId().toString();
            //需要刷新token
            String existToken = jedisCommands.get(userId + JsonWebTokenConstant.REDIS_TOKEN_SUFFIX);
            if (!StringUtils.isEmpty(existToken)) {
                logger.debug("uid:{}--------------------------------------------,JsonWebTokenVerifyStatus.CREATE_NEW", headerUserId);
                //如果redis已存在最近刚刚刷新生成的token
                response.setHeader(JsonWebTokenConstant.RESPONSE_HEADER_USER_TOKEN_FLAG, existToken);
            } else {
                //需要重新生成token 继续请求数据  把新token放在header
                logger.debug("uid:{}--------------------------------------------,JsonWebTokenVerifyStatus.CREATE_NEW,refreshGenerateTokenByRequestCode()", headerUserId);
                String allNewToken = jsonWebTokenUtils.refreshGenerateTokenByRequestCode(userId, request);
                if (StringUtils.isNotEmpty(allNewToken)) {
                    response.setHeader(JsonWebTokenConstant.RESPONSE_HEADER_USER_TOKEN_FLAG, allNewToken);
                    jedisCommands.setex(userId + JsonWebTokenConstant.REDIS_TOKEN_SUFFIX, JsonWebTokenConstant.REDIS_TOKEN_EXPIRE, allNewToken);
                } else {
                    String refreshToken = jsonWebTokenUtils.refreshGenerateTokenByDbCode(userId, request);
                    response.setHeader(JsonWebTokenConstant.RESPONSE_HEADER_USER_TOKEN_FLAG, refreshToken);
                    jedisCommands.set(userId + JsonWebTokenConstant.REDIS_TOKEN_SUFFIX, refreshToken, "NX", "EX", JsonWebTokenConstant.REDIS_TOKEN_EXPIRE);
                }
            }
            return true;
        } else if (jsonWebTokenVerifyStatus.equals(JsonWebTokenVerifyStatus.LATEST_REFRESH)) {
            String userId = CurrentUserSession.getUserId().toString();
            String existToken = jedisCommands.get(userId + JsonWebTokenConstant.REDIS_TOKEN_SUFFIX);
            if (!StringUtils.isEmpty(existToken)) {
                response.setHeader(JsonWebTokenConstant.RESPONSE_HEADER_USER_TOKEN_FLAG, existToken);
            } else {
                String refreshToken = jsonWebTokenUtils.refreshGenerateTokenByDbCode(userId, request);
                response.setHeader(JsonWebTokenConstant.RESPONSE_HEADER_USER_TOKEN_FLAG, refreshToken);
                jedisCommands.set(userId + JsonWebTokenConstant.REDIS_TOKEN_SUFFIX, refreshToken, "NX", "EX", JsonWebTokenConstant.REDIS_TOKEN_EXPIRE);
            }
            return true;
        }
        this.toOut(response, BusinessErrorCode.TO_LOGIN);
        return false;
    }

    private void toOut(HttpServletResponse response, int errorCode) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();
        JSONObject obj = new JSONObject();
        obj.put("code", errorCode);
        obj.put("message", i18Util.i18n(errorCode));
        out.write(JSON.toJSONString(obj).getBytes("utf-8"));
    }


    /**
     * 方法在业务处理器处理请求之后被调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 方法在DispatcherServlet完全处理完请求后被调用
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //移除当前userId
        CurrentUserSession.userIdThreadLocal.remove();
    }
}
