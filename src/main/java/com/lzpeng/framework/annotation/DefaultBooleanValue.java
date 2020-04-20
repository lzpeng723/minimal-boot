package com.lzpeng.framework.annotation;

import java.lang.annotation.Annotation;

/**
 * @date: 2020/3/19
 * @time: 12:59
 * @author:   李志鹏
 * BooleanValue 默认值
 */
public class DefaultBooleanValue implements BooleanValue {
    @Override
    public String trueValue() {
        return "是";
    }

    @Override
    public String falseValue() {
        return "否";
    }

    @Override
    public boolean defaultValue() {
        return false;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return BooleanValue.class;
    }
}
