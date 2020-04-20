package com.lzpeng.framework.annotation;

import java.lang.annotation.*;

/**
 * @date: 2020/3/18
 * @time: 12:24
 * @author:   李志鹏
 * 实体字段初始化字典数据
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(IntValues.class) // 组合注解
@Deprecated
public @interface IntValue {

    /**
     * 数据库中存什么值
     * @return
     */
    int key();

    /**
     * 前端展示的值
     * @return
     */
    String value();
}
