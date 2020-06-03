package com.lzpeng.framework.web.security;

import com.lzpeng.common.response.CommonCode;
import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultCode;
import com.lzpeng.common.response.ResultUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @date: 2020/3/16
 * @time: 11:50
 * @author:   李志鹏
 * 自定义 OAuth2 异常返回信息
 */
@Component
public class OAuth2ExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    @Override
    public ResponseEntity translate(Exception e) throws Exception {

        // 从OAuth2默认异常处理中拿到 OAuth2Exception
        ResponseEntity<OAuth2Exception> response = super.translate(e);
        OAuth2Exception oAuth2Exception = response.getBody();
        Result result  = convertOAuth2ExceptionToResult(oAuth2Exception);
        return ResponseEntity.status(response.getStatusCode()).body(result);

    }

    /**
     * 将 OAuth2Exception 转为可返回到前台的响应
     * @param oAuth2Exception
     * @return
     */
    private Result convertOAuth2ExceptionToResult(OAuth2Exception oAuth2Exception) {
        int httpErrorCode = oAuth2Exception.getHttpErrorCode();
        final int code = Integer.parseInt("10" + httpErrorCode);
        ResultCode resultCode = CommonCode.getCommonCode(code);
        return ResultUtil.create(resultCode, getResultData(oAuth2Exception));
    }

    /**
     * 从 oAuth2Authentication 中获取详细信息
     * @param oAuth2Authentication
     * @return
     */
    private Object getResultData(OAuth2Exception oAuth2Authentication) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", oAuth2Authentication.getOAuth2ErrorCode());
        String errorMessage = oAuth2Authentication.getMessage();
        if (errorMessage != null) {
            errorMessage = HtmlUtils.htmlEscape(errorMessage);
        }
        map.put("error_description", errorMessage);
        if ("UserDetailsService returned null, which is an interface contract violation".equals(errorMessage)) {
            map.put("msg", "用户名或密码错误");
        } else if ("Cannot convert access token to JSON".equals(errorMessage)){
            map.put("msg", "TOKEN错误");
            map.put("needReLogin", true);
        } else if (errorMessage.startsWith("Access token expired:")){
            map.put("msg", "TOKEN过期");
            map.put("needReLogin", true);
        } else {
            map.put("msg", errorMessage);
        }
        if (oAuth2Authentication.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : oAuth2Authentication.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                map.put(key, value);
            }
        }
        return map;
    }


}
