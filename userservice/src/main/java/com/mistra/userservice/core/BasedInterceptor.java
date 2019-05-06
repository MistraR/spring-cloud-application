package com.mistra.userservice.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mistra.userservice.base.JWT.*;
import com.mistra.userservice.base.exception.BusinessErrorCode;
import com.mistra.userservice.base.exception.BusinessException;
import com.mistra.userservice.base.i18n.InternationalizationUtil;
import com.mistra.userservice.utils.HttpServletHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019-05-06 10:30
 * @ Description:
 */
public class BasedInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(BasedInterceptor.class);

    private static AntPathMatcher matcher = new AntPathMatcher();

    @Autowired
    private JsonWebTokenUtils jsonWebTokenUtils;

    @Autowired
    private JsonWebTokenProperties jsonWebTokenProperties;

    @Autowired
    private InternationalizationUtil i18Util;

    /**
     * preHandle()方法在业务处理器处理请求之前被调用
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = HttpServletHelper.getRequestIpAddr(request);
        String uri = request.getRequestURI().toLowerCase();
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap != null && !parameterMap.isEmpty()) {
            Map<String, String> parameters = new LinkedHashMap<String, String>();
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                String key = entry.getKey();
                String[] valueArray = entry.getValue();
                String value = getStringArrayStr(valueArray, ",");
                parameters.put(key, value);
            }
            logger.info("requestParameter:{}", parameters);
        }
        if (isWhite(uri, jsonWebTokenProperties.getIgnoreUrl())) {
            //地址白名单不校验登陆
            logger.info("请求地址:{},路径在白名单内！不需要验证token！", request.getRequestURI());
            return true;
        } else {
            String headerUserId = request.getHeader(RequestConstans.USER_ID);
            if (StringUtils.isBlank(headerUserId)) {
                toOut(response, BusinessErrorCode.TO_LOGIN);
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
                String message = userBaseGrpcService.getLoginRecord(headerUserId, jsonWebTokenUtils.getVersionCodeFromHttpServletRequest(request).toString(), request.getIntHeader(RequestConstans.CLIENT_OS), 2).getData().toString();
                this.loginOtherMessagePrompt(BusinessErrorCode.LOGIN_OTHER, response, message);
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
        }
        toOut(response, BusinessErrorCode.TO_LOGIN);
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

    private void loginOtherMessagePrompt(Integer code, HttpServletResponse response, String message) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();
        JSONObject obj = new JSONObject();
        obj.put("code", code);
        obj.put("message", message);
        out.write(JSON.toJSONString(obj).getBytes("utf-8"));
    }

    public boolean isWhite(String path, List<String> urls) {
        if (path == null || urls == null) {
            return false;
        }
        for (String pattern : urls) {
            if (pattern.startsWith("^")) {
                return !matcher.match(pattern.split("\\^")[1], path);
            }
            if (match(path, pattern)) {
                return true;
            }
        }
        return false;
    }

    /**
     * pattern 满足过滤规则返回true.
     *
     * @param path    the path
     * @param pattern the pattern
     * @return true, if successful
     */
    public static boolean match(String path, String pattern) {
        if (path == null || pattern == null) {
            return false;
        }
        return matcher.match(pattern, path);
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
