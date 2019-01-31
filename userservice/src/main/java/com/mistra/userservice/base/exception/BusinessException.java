package com.mistra.userservice.base.exception;

/**
 * @Author: WangRui
 * @Date: 2019/1/25
 * Time: 11:21
 * Description: message换成了messageCode，通过code再去返回国际化信息
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 5317403756736254689L;

    public BusinessException(int messageCode) {
        super(String.valueOf(messageCode));
    }
}
