package com.pay.platform.security;

import java.lang.annotation.*;

/**
 * Created by zjt on 2017/1/6.
 *
 * 通用请求,标注了此注解的Controller方法将作为通用模块,任意位置可访问,不会进行权限拦截
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommonRequest {

}