package com.mistra.userservice.core.exception;

import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2019/1/25
 * Time: 11:21
 * Description: message换成了messageCode，通过code再去返回国际化信息
 */
@Data
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 5317403756736254689L;

    private int code;

    private Object[] args;
    public BusinessException(int messageCode) {
        super(getCodeMessage(messageCode));
        this.code = messageCode;
    }
    public BusinessException(int messageCode,Object... args) {
        super(getCodeMessage(messageCode));
        this.code = messageCode;
        this.args = args;
    }
    private static String getCodeMessage(int messageCode) {
        List<String> fieldName = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Class businessErrorCode = classLoader.loadClass("com.mechat.common.BusinessErrorCode");
            Field[] fields = businessErrorCode.getDeclaredFields();
            List<Field> fieldList = Arrays.asList(fields);
            fieldList.stream().forEach(field -> {
                try {
                    field.isAccessible();
                    if (Integer.parseInt(field.get(businessErrorCode).toString()) == messageCode) {
                        fieldName.add(field.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return fieldName.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return "FAIL";
        }
    }
}
