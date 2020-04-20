package com.lzpeng.framework.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsUtils;

/**
 * 核心配置
 * @date: 2019/12/8
 * @time: 11:38
 * @author: 李志鹏
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @ConditionalOnMissingBean(value = PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .formLogin() // 表单登录 用户名密码登录 TODO　之后要替换为 jwt token
                .loginPage("/login.html")
                .loginProcessingUrl("/sys/user/login")
//                .successHandler(authenticationSuccessHandler)
//                .failureHandler(authenticationFailureHandler)
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() //对preflight放行
                .mvcMatchers("/js/**", "/vendor/**", "/css/**", "/login.html", "/actuator/**", "/doc.html/**", "/ sys/user/login", "/webjars/**", "/swagger-ui.html/**", "/swagger-resources/**", "/v2/api-docs") // 这些请求
                .permitAll() // 不需要身份认证
                .anyRequest() //其它所有请求
                .authenticated() //需要身份认证
                .and().cors()
                .and().csrf().disable(); //取消CSRF 防御
    }

    @Override
    @Bean
    @ConditionalOnMissingBean(value = UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Autowired
            private PasswordEncoder passwordEncoder;

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                String authority;
                if ("admin".equals(username) || "administrator".equals(username)) {
                    authority = "ROLE_ADMIN";
                } else if ("user".equals(username)) {
                    authority = "ROLE_USER";
                } else {
                    return null;
                }
                return new User(username, passwordEncoder.encode("123456"),
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authority));
            }
        };
    }

}
