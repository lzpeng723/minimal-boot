package com.lzpeng.framework.web.config;

import com.lzpeng.framework.domain.IntEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * String 转 枚举
 *
 * @date: 2020/5/17
 * @time: 1:24
 * @author: 李志鹏
 */
public class StringToIntEnumConverterFactory implements ConverterFactory<String, IntEnum> {


    /**
     * int 类型转换公材
     */
    private IntegerToEnumConverterFactory integerToEnumConverterFactory = new IntegerToEnumConverterFactory();

    /**
     * String 类型的 Convert
     *
     * @param targetType
     * @param <T>
     * @return
     */
    @Override
    public <T extends IntEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return source -> {
            // 如果是数字进入数字转化逻辑
            int intValue = Integer.parseInt(source);
            return integerToEnumConverterFactory.getConverter(targetType).convert(intValue);
        };
    }
}
