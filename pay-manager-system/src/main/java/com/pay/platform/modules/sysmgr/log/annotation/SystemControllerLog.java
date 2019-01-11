package com.pay.platform.modules.sysmgr.log.annotation;

import java.lang.annotation.*;

/**
 * Created by zjt on 2016/10/22.
 *
 * 自定义注解,在运行期间拦截Controller,写入日志
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemControllerLog {

    /**
     * 模块: 例如用户管理
     * @return
     */
    String module() default "";

    /**
     * 操作: 例如新增用户
     * @return
     */
    String operation() default "";

}