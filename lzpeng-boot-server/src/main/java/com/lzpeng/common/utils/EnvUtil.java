package com.lzpeng.common.utils;

import cn.hutool.core.lang.caller.CallerUtil;

import java.net.URL;

/**
 * 当前环境信息
 * @date: 2020/4/20
 * @time: 23:23
 * @author: 李志鹏
 */
public class EnvUtil {

    /**
     * 当前环境是否是开发环境
     * 待检验 war 包部署是否可行
     * @return
     */
    public static boolean isDev(){
        Class<?> caller = CallerUtil.getCallerCaller();
        URL url = caller.getClassLoader().getResource(caller.getName().replace(".", "/") + ".class");
        boolean isDev = "file".equalsIgnoreCase(url.getProtocol());
        return isDev;
    }

    /**
     * 当前环境是否是正式环境
     * 待检验 war 包部署是否可行
     * @return
     */
    public static boolean isProd(){
        return !isDev();
    }
}
