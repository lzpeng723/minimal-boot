package com.lzpeng.framework.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Token 存储配置
 * @date: 2020/2/25
 * @time: 15:48
 * @author: 李志鹏
 * //@Configuration
 */
public class RedisTokenConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * redis 存储 token
     * @return
     */
    @Bean
    public TokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

}
