package com.lzpeng.framework.util;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @date: 2020/3/20
 * @time: 10:00
 * @author:   李志鹏
 * 线程绑定变量维护
 */
public class ThreadLocalUtil {

    /**
     * 当前用户
     * 拦截器在每个请求后清除当前线程用户
     */
    public static final ThreadLocal<UserDetails> USER = new ThreadLocal<>();
}
