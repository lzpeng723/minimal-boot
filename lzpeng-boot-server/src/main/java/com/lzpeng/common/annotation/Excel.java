package com.lzpeng.common.annotation;

import java.lang.annotation.*;

/**
 * 导出Excel使用此注解
 * @date: 2020/4/17
 * @time: 12:48
 * @author:   李志鹏
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Excel {

    /**
     * 是否导入此属性
     * @return
     */
    boolean imported() default true;

    /**
     * 是否导出此属性
     * @return
     */
    boolean exported() default true;

    /**
     * 属性导入导出时的列名
     * @return
     */
    String name();

    /**
     * 属性为空时默认导出内容
     * @return
     */
    String defaultValue() default "";
}
