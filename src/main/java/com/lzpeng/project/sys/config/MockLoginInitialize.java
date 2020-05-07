package com.lzpeng.project.sys.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 模拟用户登录
 * @date: 2020/5/4
 * @time: 18:31
 * @author: 李志鹏
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@ConditionalOnWebApplication
public class MockLoginInitialize  implements ApplicationRunner {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 登录用户
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("anonymous", "123456"));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
