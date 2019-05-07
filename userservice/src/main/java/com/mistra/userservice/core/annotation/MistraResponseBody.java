package com.mistra.userservice.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author: WangRui
 * @Date: 2019/1/25
 * Time: 10:42
 * Description: 拦截Controller返回值自动转json返回，不再需要@ResponseBody注解
 */
@Retention(RUNTIME)
@Target({METHOD})
@Documented
public @interface MistraResponseBody {
}
