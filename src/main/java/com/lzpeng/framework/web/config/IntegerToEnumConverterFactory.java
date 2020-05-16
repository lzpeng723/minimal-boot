package com.lzpeng.framework.web.config;

import com.lzpeng.framework.domain.IntEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * integer 转 枚举
 *
 * @date: 2020/5/17
 * @time: 1:24
 * @author: 李志鹏
 */
public class IntegerToEnumConverterFactory implements ConverterFactory<Integer, IntEnum> {

    /**
     * integer 转为 targetType
     *
     * @param targetType
     * @param <T>
     * @return
     */
    @Override
    public <T extends IntEnum> Converter<Integer, T> getConverter(Class<T> targetType) {
        return source -> {
            if (!targetType.isEnum()) {
                throw new UnsupportedOperationException(targetType.getName() + "不是枚举, 请检查");
            }
            T[] enums = targetType.getEnumConstants();
            for (T anEnum : enums) {
                if (anEnum.getCode().equals(source)) {
                    return anEnum;
                }
            }
            throw new UnsupportedOperationException("枚举转化异常。枚举【" + targetType.getName() + "】, mvc传入的值为：【" + source + "】");
        };

    }
}
