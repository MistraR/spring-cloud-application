package com.mistra.base.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019-05-06 10:32
 * @ Description:
 */
public class HttpServletHelper {

    private static Logger logger = LoggerFactory.getLogger(HttpServletHelper.class);


    public static String getAttribute(HttpServletRequest request, String key) {
        if (StringUtils.isEmpty(key)) {
            return "";
        }
        Object attribute = request.getAttribute(key);
        return attribute == null ? "" : attribute.toString();
    }

    public static void setAttribute(HttpServletRequest request, String key, String value) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        request.setAttribute(key, value);
    }

    public static boolean isAjax(HttpServletRequest request) {
        return StringUtils.equals(request.getHeader("X-Requested-With"), "XMLHttpRequest");
    }

    public static void delCookie(HttpServletResponse response, String key) {
        try {
            Cookie name = new Cookie(key, null);
            name.setMaxAge(0);
            response.addCookie(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCookie(HttpServletResponse response, String key, String value) {
        try {
            // 为名字和姓氏创建 Cookie
            // 中文转码
            Cookie name = new Cookie(key, URLEncoder.encode(value, "UTF-8"));
            name.setMaxAge(60 * 60 * 24);
            // 在响应头中添加两个 Cookie
            response.addCookie(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCookie(HttpServletRequest request, String cookieName) {
        //这样便可以获取一个cookie数组
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * <p>
     * 获取用户IP地址
     * </P>
     * Date 2013-11-11 上午11:45:00
     *
     * @param request
     * @return IP地址
     * @throws Exception
     */
    public static String getRequestIpAddr(HttpServletRequest request) {
        try {
            String ip = getIpAddr(request, "X-Forwarded-For");
            if (StringUtils.isNotEmpty(ip) && ip.length() > 15) {
                String[] ips = ip.split(",");
                for (int index = 0; index < ips.length; index++) {
                    String strIp = ips[index];
                    if (!("unknown".equalsIgnoreCase(strIp))) {
                        ip = strIp;
                        break;
                    }
                }
                return ip;
            }
            if (StringUtils.isNotEmpty(ip)) {
                return ip;
            }
            ip = getIpAddr(request, "Proxy-Client-IP");
            if (StringUtils.isNotEmpty(ip)) {
                return ip;
            }
            ip = getIpAddr(request, "WL-Proxy-Client-IP");
            if (StringUtils.isNotEmpty(ip)) {
                return ip;
            }
            ip = getIpAddr(request, "HTTP_CLIENT_IP");
            if (StringUtils.isNotEmpty(ip)) {
                return ip;
            }
            ip = getIpAddr(request, "HTTP_X_FORWARDED_FOR");
            if (StringUtils.isNotEmpty(ip)) {
                return ip;
            }
            ip = request.getRemoteAddr();
            return ip;
        } catch (Exception e) {
            logger.error("http servlet helper,get request ip address fail,error message:{}", e.getMessage(), e);
        }
        return "";
    }

    /**
     * <p>
     * 通过AJAX返回页面JSON数据公用方法
     * </P>
     * <p>
     * Date 2013-2-19 下午4:54:06<br/>
     *
     * @param response HttpServletResponse对象
     * @param data     返回数据
     */
    protected static void build(HttpServletResponse response, Object data) {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        try {
            response.getWriter().write(data != null ? data.toString() : "DATA_IS_NULL");
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("通过AJAX返回页面JSON数据公用方法:{}", e.getMessage(), e);
        }
    }

    private static String getIpAddr(HttpServletRequest request, String header) {
        String ip = request.getHeader(header);
        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            return "";
        }
        return ip;
    }

}
