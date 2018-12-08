package com.mistra.routeservice.filter;

import com.mistra.base.JWT.JWTUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: WangRui
 * @Time: 2018/9/19/003
 * Describe: Zuul 前置过滤器
 */
@Component
public class MyFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(MyFilter.class);

    final private static String loginFlag = "/user/login";

    @Autowired
    private JWTUtil jwtUtil;

    /**
     * 前置过滤器
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 优先级为0，数字越大，优先级越低
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否执行该过滤器，此处为true，说明需要过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
     * pre：路由之前
     * routing：路由之时
     * post： 路由之后
     * error：发送错误调用
     * filterOrder：过滤的顺序
     * shouldFilter：这里可以写逻辑判断，是否要过滤，本文true,永远过滤。
     * run：过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
     * 访问测试：http://localhost:8888/api-feign/hi?name=forezp  ||  http://localhost:8888/api-feign/hi?name=forezp&token=22
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();
        HttpServletResponse httpServletResponse = requestContext.getResponse();
        httpServletResponse.setIntHeader("zuul_code", 200);
        requestContext.setSendZuulResponse(true);
        logger.info("{} >>> {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURL());
        String token = jwtUtil.getToken(httpServletRequest);
        // 在zuul进行token认证  除登陆接口以外的接口都要经过这个判断
//        if (token == null) {
//            if (!httpServletRequest.getRequestURI().endsWith(loginFlag)) {
//                requestContext.setSendZuulResponse(false);
//            }
//        } else {
//            Integer code = jwtUtil.verification(token).getCode();
//            if (code.equals(JWTVerifyStatus.CREATE_NEW.getCode())) {
//                //需要重新生成token
//                //不对该请求进行路由
//                requestContext.setSendZuulResponse(false);
//                //header的key相同时会覆盖value
//                httpServletResponse.setHeader("zuulCode", "10001");
//                httpServletResponse.setHeader("token", jwtUtil.generateToken(jwtUtil.parseTokenGetUserId(token)));
//            } else if (code.equals(JWTVerifyStatus.LOGIN.getCode())){
//                //需要重新登录
//                requestContext.setSendZuulResponse(false);
//                httpServletResponse.setHeader("zuulCode", "10002");
//            }
//        }

        //不在zuul进行token认证，让userservice服务自己认证
        if (token == null) {
            requestContext.setSendZuulResponse(false);
            httpServletResponse.setIntHeader("zuul_code", 403);
            if (httpServletRequest.getRequestURI().endsWith(loginFlag)) {
                requestContext.setSendZuulResponse(true);
                httpServletResponse.setIntHeader("zuul_code", 200);
            }
        }
        return null;
    }

}
