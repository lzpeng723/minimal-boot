package com.lzpeng.framework.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

/**
 * 授权码模式 https://tools.ietf.org/html/rfc6749#section-4.1
 * 获取授权码 GET: http://127.0.0.1:9001/oauth/authorize?response_type=code&client_id=lzpeng&redirect_uri=http://www.baidu.com&scope=all RETURN: http://www.baidu.com?code=授权码
 * 获取TOKEN POST: http://127.0.0.1:9001/oauth/token Header: Authorization=Basic client_id client_secret BODY: {"grant_type":"authorization_code","redirect_uri":"http://www.baidu.com","scope":"all","client_id":"lzpeng","code":"授权码"} RETURN: {"access_token": "91af061f-f849-45cc-a7d2-6fa2e6e0a234","token_type": "bearer","refresh_token": "3ac59cda-ded6-4985-8a9b-cf5f807eb4e9","expires_in": 43199,"scope": "all"}
 * 密码模式获取TOKEN: POST: http://127.0.0.1:9001/oauth/token Header: Authorization=Basic client_id client_secret BODY: {"grant_type":"password","redirect_uri":"http://www.baidu.com","scope":"all","username":"user","code":"授权码","password":"123456"} RETURN: {"access_token": "91af061f-f849-45cc-a7d2-6fa2e6e0a234","token_type": "bearer","refresh_token": "3ac59cda-ded6-4985-8a9b-cf5f807eb4e9","expires_in": 43199,"scope": "all"}
 * 获取用户信息: GET: http://127.0.0.1:9001/sys/user/me Header: Authorization=Bearer 8db4c530-8c10-4118-b733-aeccb2fb60e1
 * 获取TOKEN: POST: POST: http://127.0.0.1:9001/oauth/token Header: Authorization=Basic client_id client_secret BODY: {"grant_type":"refresh_token", "refresh_token":"刷新", "scope":"all"}
 * 启用认证服务器
 * @date: 2020/2/24
 * @time: 21:57
 * @author: 李志鹏
 */
@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(OAuth2Properties.class) // 使 OAuth2Properties.class 配置生效
public class AuthenticationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenStore jwtTokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenEnhancer jwtTokenEnhancer;

    @Autowired
    private OAuth2ExceptionTranslator oAuth2ExceptionTranslator;

    @Autowired
    private OAuth2Properties oAuth2Properties;


    /**
     * 配置端点
     * jwt 增强器链
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(jwtTokenEnhancer, jwtAccessTokenConverter));
        endpoints.authenticationManager(authenticationManager) // 设置 security 的 authenticationManager
                .userDetailsService(userDetailsService) // 设置获取用户 UserDetailsService
                .tokenStore(jwtTokenStore) // 设置为 jwt token
                .tokenEnhancer(enhancerChain)// 设置 token 增强器链
                .accessTokenConverter(jwtAccessTokenConverter) // 设置 accessToken 转换器
                .exceptionTranslator(oAuth2ExceptionTranslator); // 设置异常处理器，在这里设置无效，需要在@EnableResourceServer所注解的类中设置
    }

    /**
     * 客户端配置
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(oAuth2Properties.getClientId())
                .secret(passwordEncoder.encode(oAuth2Properties.getClientSecret()))
                .accessTokenValiditySeconds(oAuth2Properties.getAccessTokenValiditySeconds())
                .refreshTokenValiditySeconds(oAuth2Properties.getRefreshTokenValiditySeconds())
                .authorizedGrantTypes(oAuth2Properties.getGrantType())
                .scopes(oAuth2Properties.getScopes())
                .redirectUris(oAuth2Properties.getRegisteredRedirectUri());
    }

    /**
     * 授权后可以获取 token-key
     * 单点登录使用
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("isAuthenticated()");
    }
}
