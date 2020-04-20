package com.lzpeng.framework.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.HashMap;
import java.util.Map;

/**
 * Token 存储配置
 * @date: 2020/2/25
 * @time: 15:48
 * @author: 李志鹏
 */
@Configuration
public class JwtTokenConfig {

    /**
     * 对Jwt签名时，增加一个密钥
     * JwtAccessTokenConverter：对Jwt来进行编码以及解码的类
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("lzpeng-secret");
        return converter;
    }

    /**
     * 设置token 由Jwt产生，不使用默认的透明令牌
     */
    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public TokenEnhancer jwtTokenEnhancer(){
        return (accessToken, authentication) -> {
            Object principal = authentication.getPrincipal();
            Map<String, Object> map = new HashMap<>();
            if (principal instanceof UserDetails) {
                map.put("username", ((UserDetails) principal).getUsername());
            }
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
            return accessToken;
        };
    }
}
