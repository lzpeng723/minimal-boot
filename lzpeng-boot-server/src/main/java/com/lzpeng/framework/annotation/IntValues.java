package com.lzpeng.framework.annotation;

import java.lang.annotation.*;

/**
 * @date: 2020/3/18
 * @time: 12:24
 * @author:   李志鹏
 * 实体字段初始化字典数据集合
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated
public @interface IntValues {

    IntValue[] value();
}
