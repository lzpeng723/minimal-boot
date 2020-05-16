package com.lzpeng.framework.domain;

import cn.hutool.core.util.TypeUtil;
import org.springframework.core.convert.converter.Converter;

import javax.persistence.AttributeConverter;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * 枚举转换器
 *
 * @date: 2020/4/5
 * @time: 0:18
 * @author: 李志鹏
 * @see {https://blog.csdn.net/wanping321/article/details/90269057}
 */

/**
 * 枚举转换器
 *
 * @param <ATTR> 枚举类型
 * @param <DB>   数据库字段类型 或 MVC接收参数类型
 * @date: 2020/4/5
 * @time: 0:18
 * @author: 李志鹏
 * @see {https://blog.csdn.net/wanping321/article/details/90269057} AttributeConverter JPA读取数据库使用
 * @see {https://blog.csdn.net/weixin_41249041/article/details/96764460} Converter MVC 转换参数使用
 */
public abstract class BaseEnumConverter<ATTR extends BaseEnum<DB>, DB> implements AttributeConverter<ATTR, DB>, Converter<DB, ATTR> {

    /**
     * 将枚举转换为数据库要存储的值
     *
     * @param attribute 枚举值
     * @return
     */
    @Override
    public DB convertToDatabaseColumn(ATTR attribute) {
        Optional<DB> optional = Optional.ofNullable(attribute).map(attr -> attr.getCode());
        return optional.orElse(null);
    }

    /**
     * 将数据库存储的值转为枚举
     *
     * @param dbData 数据库存储的值
     * @return
     */
    @Override
    public ATTR convertToEntityAttribute(DB dbData) {
        // 得到真实枚举类型
        Type type = TypeUtil.getTypeArgument(getClass());
        Class<ATTR> clazz = (Class<ATTR>) type;
        if (!clazz.isEnum()) {
            throw new UnsupportedOperationException(clazz.getName() + "不是枚举, 请检查");
        }
        ATTR[] enums = clazz.getEnumConstants();
        for (ATTR anEnum : enums) {
            if (anEnum.getCode().equals(dbData)) {
                return anEnum;
            }
        }
        throw new UnsupportedOperationException("枚举转化异常。枚举【" + clazz.getName() + "】,数据库库中的值为：【" + dbData + "】");
    }

    /**
     * 将 mvc 传入的参数转化为枚举
     *
     * @param source
     * @return
     */
    @Override
    public ATTR convert(DB source) {
        return convertToEntityAttribute(source);
    }
}
