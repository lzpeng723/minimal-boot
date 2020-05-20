package com.lzpeng.framework.annotation;

import java.lang.annotation.*;

/**
 * @date: 2020/3/9
 * @time: 15:48
 * @author:   李志鹏
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodLog {
}
