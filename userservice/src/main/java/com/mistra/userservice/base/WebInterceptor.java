package com.mistra.userservice.base;

import com.mistra.base.JWT.JsonWwbTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: WangRui
 * @Date: 2018/11/30
 * Time: 16:51
 * Description: 拦截器
 */
@Component
public class WebInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(WebInterceptor.class);

    @Autowired
    private JsonWwbTokenUtil jwtUtil;

    /**
     * 验证token,通常情况下结合redis做刷新token缓存，比如同一个页面有多个请求时需要刷新token不用每次都重新生成，在redis拿
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("{} >>> {}", request.getMethod(), request.getRequestURL());
        String url = request.getRequestURI();
//        String token = jwtUtil.getToken(request);
//        Integer code = jwtUtil.verification(token).getCode();
//        if (code.equals(JsonWwbTokenVerifyStatus.SUCCESS.getCode())) {
//            return true;
//        } else if (code.equals(JsonWwbTokenVerifyStatus.CREATE_NEW.getCode())) {
//            String userId = jwtUtil.parseTokenGetUserId(token);
//            String allNewToken = jwtUtil.generateToken(userId);
//            response.setHeader(JsonWebTokenConstant.RESPONSE_HEADER_USER_TOKEN_FLAG, allNewToken);
//            return true;
//        } else if (code.equals(JsonWwbTokenVerifyStatus.LOGIN.getCode())) {
//            throw new BaseServiceException(ResultCode.LOGIN_EXPIRED_ERROR);
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }
}
