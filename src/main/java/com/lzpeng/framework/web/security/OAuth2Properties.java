package com.lzpeng.framework.web.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * OAuth2 属性配置
 * @date: 2020/4/4
 * @time: 21:31
 * @author:   李志鹏
 */
@Data
@ConfigurationProperties(prefix = "security.oauth2.client")
public class OAuth2Properties {
    private String clientId;
    private String clientSecret;
    private String registeredRedirectUri;
    private String[] grantType;
    private String[] scopes;
    private String[] permitAllUrls = new String[0];
    /**
     * accessToken过期时间 秒
     */
    private int accessTokenValiditySeconds;
    /**
     * refreshToken过期时间 秒
     */
    private int refreshTokenValiditySeconds;
}
