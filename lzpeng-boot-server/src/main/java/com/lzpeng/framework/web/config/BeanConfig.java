package com.lzpeng.framework.web.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @date: 2020/3/25
 * @time: 1:03
 * @author:   李志鹏
 */
@Configuration
public class BeanConfig {

    @Bean
    public Snowflake snowflake(){
        return IdUtil.createSnowflake(0L,0L);
    }
}
