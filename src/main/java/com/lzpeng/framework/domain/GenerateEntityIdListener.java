package com.lzpeng.framework.domain;

import cn.hutool.core.lang.Snowflake;
import com.lzpeng.common.utils.Radix;
import com.lzpeng.common.utils.StringConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.PrePersist;
import java.util.regex.Pattern;

/**
 * id 生成器
 * 生成规则 实体类全路径截取跟包之后 + snowflake 生成的id 并转为16进制大写
 * @date: 2020/2/1
 * @time: 22:28
 * @author: 李志鹏
 */
@Component
public class GenerateEntityIdListener {

    @Autowired
    private Snowflake snowflake;

    private String pattern = StringConstant.ENTITY_NAME_PATTERN;

    @PrePersist
    public void touchForCreate(Object target) {
        Assert.notNull(target, "Entity must not be null!");
        if (target instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) target;
            String clazzName = entity.getClass().getName();
            if (!Pattern.compile(pattern).matcher(clazzName).matches()){
                throw new RuntimeException("错误: " + clazzName + " 不符合正则表达式 " + pattern);
            }
            long longId = snowflake.nextId();
            String entityId = Radix.encodeEntityId(entity.getClass(), longId); // 编码实体id
            entity.setId(entityId);
        }
    }


}
