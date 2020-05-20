package com.lzpeng.framework.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Component;

import java.util.Collections;


/**
 * @date: 2020/3/6
 * @time: 18:28
 * @author:   李志鹏
 */
@Component
public class TokenFactory {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Qualifier("defaultAuthorizationServerTokenServices")
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;


    private ClientDetails getClientDetails(String clientId) {

        return clientDetailsService.loadClientByClientId(clientId);
    }

    /**
     *
     * @param clientDetails
     * @param authentication
     * @return
     */
    private OAuth2AccessToken getAccessToken(ClientDetails clientDetails, Authentication authentication) {
        TokenRequest tokenRequest = new TokenRequest(Collections.emptyMap(), clientDetails.getClientId(), clientDetails.getScope(), "password");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        return authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
    }

    /**
     *
     * @param clientId
     * @param username
     * @param password
     * @return OAuth2AccessToken
     */
    public OAuth2AccessToken getAccessToken(String clientId, String username, String password) {
        ClientDetails clientDetails = getClientDetails(clientId);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return getAccessToken(clientDetails, authentication);
    }

}