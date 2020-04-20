package com.lzpeng.framework.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsUtils;

/**
 * 资源服务器
 * security 配置
 * @date: 2020/2/24
 * @time: 22:39
 * @author: 李志鹏
 * 有这个注解 不能 浏览器 单点登录, 不能 druid 监控
 * 无这个注解 不能前后端分离
 * // 使 OAuth2Properties.class 配置生效
 */
@Configuration
@EnableResourceServer
@EnableConfigurationProperties(OAuth2Properties.class)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private OAuth2ExceptionTranslator oAuth2ExceptionTranslator;

    @Autowired
    private OAuth2Properties oAuth2Properties;

    private String[] urls = new String[] {"/druid/**", "/actuator/**", "/doc.html/**", "/ sys/user/login", "/webjars/**", "/swagger-ui.html/**", "/swagger-resources/**", "/v2/api-docs"};

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        // 定义异常转换类生效， 使用自己定义的OAuth2异常处理器
        AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        ((OAuth2AuthenticationEntryPoint) authenticationEntryPoint).setExceptionTranslator(oAuth2ExceptionTranslator);
        resources.authenticationEntryPoint(authenticationEntryPoint);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/druid/**").permitAll();
        String[] customUrls = oAuth2Properties.getPermitAllUrls();
        String[] allUrls = new String[customUrls.length + urls.length];
        System.arraycopy(urls, 0, allUrls, 0, urls.length);
        System.arraycopy(customUrls, 0, allUrls, urls.length, customUrls.length);
        http
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() //对preflight放行
                .mvcMatchers(allUrls) // 这些请求
                .permitAll() // 不需要身份认证
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest) //对preflight放行.permitAll()
                .permitAll() // 不需要身份认证
                //.antMatchers("/sys/**").hasAnyRole("ADMIN")
                .anyRequest() //其它所有请求
                .authenticated() //需要身份认证
                .and().headers().frameOptions().disable() // 取消iframe防御
                .and().cors()
                .and().csrf().disable(); //取消CSRF 防御
    }

}
