package com.lzpeng.framework.model;

import cn.hutool.http.useragent.UserAgent;
import lombok.Data;

/**
 * @date: 2020/3/9
 * @time: 16:40
 * @author:   李志鹏
 * 请求中 User-Agent 信息
 */
@Data
public class UA {

    /** 是否为移动平台 */
    private boolean mobile;
    /** 浏览器类型 */
    private String browser;
    /** 平台类型 */
    private String platform;
    /** 系统类型 */
    private String os;
    /** 引擎类型 */
    private String engine;
    /** 浏览器版本 */
    private String version;
    /** 引擎版本 */
    private String engineVersion;

    private UA(){}

    public static UA create(UserAgent userAgent){
        UA ua = new UA();
        ua.mobile = userAgent.isMobile();
        ua.browser = userAgent.getBrowser().getName();
        ua.platform = userAgent.getPlatform().getName();
        ua.os = userAgent.getOs().getName();
        ua.engine = userAgent.getEngine().getName();
        ua.version = userAgent.getVersion();
        ua.engineVersion = userAgent.getEngineVersion();
        return ua;
    }
}
