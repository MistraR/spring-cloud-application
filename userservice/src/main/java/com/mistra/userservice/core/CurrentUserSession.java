package com.mistra.userservice.core;

/**
 * @Author: WangRui
 * @Date: 2019/1/25
 * Time: 09:44
 * Description: 线程本地变量备份当前请求的用户id，也可以改成存储用户对象User
 */
public class CurrentUserSession {

    public static ThreadLocal<Long> userIdThreadLocal = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        userIdThreadLocal.set(userId);
    }

    public static Long getUserId() {
        return userIdThreadLocal.get();
    }
}
