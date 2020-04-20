package com.lzpeng.framework.web.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录失败处理
 * @date: 2020/2/24
 * @time: 21:54
 * @author: 李志鹏
 */
@Slf4j
@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @param request
     * @param response
     * @param e        登录过程中的错误
     * @throws IOException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.info("认证失败: {}", e.getMessage());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        //noinspection AliDeprecation
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Map<String, Object> result = new HashMap<>(2);
        result.put("e", e.getClass().getName());
        result.put("message", e.getMessage());
        //向前台返回登录失败信息
        response.getWriter().write(objectMapper.writeValueAsString(result));

    }
}

