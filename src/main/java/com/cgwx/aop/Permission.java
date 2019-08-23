package com.cgwx.aop;

import java.lang.annotation.*;

/**
 * 权限控制器
 * @author panshengnan
 * @Date 2019年8月12日
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permission {

    //控制业务系统权限
    //注意事项：使用此注解开发controller时请将返回类型设置成object，以免出现问题


}
