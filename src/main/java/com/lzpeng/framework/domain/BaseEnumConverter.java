package com.lzpeng.framework.domain;

import cn.hutool.core.util.TypeUtil;

import javax.persistence.AttributeConverter;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * 枚举转换器
 * @date: 2020/4/5
 * @time: 0:18
 * @author:   李志鹏
 * @see {https://blog.csdn.net/wanping321/article/details/90269057}
 */
public abstract class BaseEnumConverter<ATTR extends Enum<ATTR> & BaseEnum<DB>, DB> implements AttributeConverter<ATTR, DB> {
    @Override
    public DB convertToDatabaseColumn(ATTR attribute) {
        Optional<DB> optional = Optional.ofNullable(attribute).map(attr -> attr.getCode());
        return optional.orElse(null);
    }

    @Override
    public ATTR convertToEntityAttribute(DB dbData) {
        Type type = TypeUtil.getTypeArgument(getClass()); // 得到真实枚举类型
        Class<ATTR> clazz = (Class<ATTR>) type;
        ATTR[] enums = clazz.getEnumConstants();
        for (ATTR anEnum : enums) {
            if (anEnum.getCode().equals(dbData)) {
                return anEnum;
            }
        }
        throw new UnsupportedOperationException("枚举转化异常。枚举【" + clazz.getName() + "】,数据库库中的值为：【" + dbData + "】");
    }
}
