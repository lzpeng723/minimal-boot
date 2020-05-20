package com.lzpeng.framework.web.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * 自定义登录成功逻辑
 * @date: 2020/2/24
 * @time: 21:45
 * @author: 李志鹏
 */
@Slf4j
@Component
public class AuthenticationSuccessHandler  extends SavedRequestAwareAuthenticationSuccessHandler {

    public static final String AUTHENTICATION_SCHEME_BASIC = "Basic";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices jwtTokenServices;


    /**
     * 登录成功处理
     *
     * @param request
     * @param response
     * @param authentication 封装了认证信息，认证请求ip，session，UserDetail
     * @throws IOException
     */
    @SuppressWarnings("AliDeprecation")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String clientId = request.getParameter("client_id");
        String clientSecret = "";
        if (clientId != null && !StringUtils.isEmpty(clientId)){
            clientSecret = request.getParameter("client_secret");
        } else {
            String token = getAuthenticationByHeader(request);
            int delim = token.indexOf(":");
            clientId = token.substring(0, delim);
            clientSecret = token.substring(delim + 1);
        }
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId 对应配置信息不存在 " + clientId);
        } else if (!Objects.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("clientSecret 不匹配");
        }
        TokenRequest tokenRequest = new TokenRequest(Collections.emptyMap(), clientDetails.getClientId(), clientDetails.getScope(), "custom");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken accessToken = jwtTokenServices.createAccessToken(oAuth2Authentication);
        log.info(" {} 认证成功", authentication.getName());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        //向前台返回登录认证信息
        response.getWriter().write(objectMapper.writeValueAsString(accessToken));

    }

    /**
     * 获取 clientId clientSecret
     * @see {org.springframework.security.web.authentication.www.BasicAuthenticationConverter#convert(javax.servlet.http.HttpServletRequest)}
     * @param request
     * @return
     */
    public String getAuthenticationByHeader(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        if (header == null) {
            throw new UnapprovedClientAuthenticationException("请求中无Client信息");
        }

        header = header.trim();
        if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {
            throw new UnapprovedClientAuthenticationException("请求中无Client信息");
        }

        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }
        String token = new String(decoded, StandardCharsets.UTF_8);
        return token;
    }
}
