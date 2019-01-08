package com.neo.aop;

import java.lang.annotation.*;

/**
 * @Auther: xuwen
 * @Date: 2018/7/17 18:07
 * @Description:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckLogin {

}
