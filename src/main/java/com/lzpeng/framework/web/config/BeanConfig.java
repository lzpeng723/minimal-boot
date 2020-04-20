package com.lzpeng.framework.web.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @date: 2020/3/25
 * @time: 1:03
 * @author:   李志鹏
 */
@Configuration
public class BeanConfig {


    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // 忽略空属性
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // https://blog.csdn.net/J080624/article/details/82529082
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return mapper;
    }

    @Bean
    public Snowflake snowflake(){
        return IdUtil.createSnowflake(0L,0L);
    }
}
