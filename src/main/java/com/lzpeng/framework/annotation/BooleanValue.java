package com.lzpeng.framework.annotation;

import java.lang.annotation.*;

/**
 * @date: 2020/3/18
 * @time: 12:24
 * @author:   李志鹏
 * 实体字段初始化字典数据
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BooleanValue {

    /**
     * 数据库中存 true 时前台展示什么
     * @return
     */
    String trueValue() default "是";

    /**
     * 数据库中存 false 时前台展示什么
     * @return
     */
    String falseValue() default "否";

    /**
     * 数据库中为null用true还是false
     * @return
     */
    boolean defaultValue() default false;
}
