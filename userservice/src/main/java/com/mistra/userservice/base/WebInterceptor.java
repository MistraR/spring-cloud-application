package com.mistra.userservice.base;

import com.mistra.base.JWT.JWTUtil;
import com.mistra.base.JWT.JWTVerifyStatus;
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
    private JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("preHandle()方法！");
        logger.info("{} >>> {}", request.getMethod(), request.getRequestURL());
        response.setIntHeader("pre_code", 200);
        String token = jwtUtil.getToken(request);
        Integer code = jwtUtil.verification(token).getCode();
        boolean isContinue = true;
        if (code.equals(JWTVerifyStatus.CREATE_NEW.getCode())) {
            //需要重新生成token 继续请求数据  把新token放在header
            response.setIntHeader("pre_code", 10001);
            response.setHeader("token", jwtUtil.generateToken(jwtUtil.parseTokenGetUserId(token)));
        } else if (code.equals(JWTVerifyStatus.LOGIN.getCode())) {
            //需要重新登录
            response.setIntHeader("pre_code", 10002);
            isContinue = false;
        }
        return isContinue;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        logger.info("进入postHandle()方法！");
        response.setIntHeader("post_code",20000);
    }
}
