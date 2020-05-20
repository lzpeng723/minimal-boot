/**
 *
 */
package com.lzpeng.framework.web.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzpeng.framework.web.config.UserAuditor;
import com.lzpeng.framework.util.ThreadLocalUtil;
import com.lzpeng.project.monitor.domain.RequestLog;
import com.lzpeng.project.monitor.domain.UA;
import com.lzpeng.project.monitor.repository.RequestLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

/**
 * 通过拦截器实现计时功能
 * 可以获取请求调用的 控制器和方法
 * 拦截器不仅会拦截自己写的控制器，也会拦截框架里的控制器
 *
 * 拦截器不能获取方法真正的值，可以通过 AOP 获取
 * @author 李志鹏
 * TODO: request 中 body 只能读取一次
 *
 */
@Slf4j
@Component
public class RequestLogInterceptor implements HandlerInterceptor {

    @Autowired
    private RequestLogRepository requestLogRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserAuditor userAuditor;

    /** 请求前调用
     * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String ip = ServletUtil.getClientIP(request);
        Map<String, String[]> params = ServletUtil.getParams(request);
        String ua = request.getHeader(HttpHeaders.USER_AGENT);
        UserAgent userAgent = UserAgentUtil.parse(ua);
        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        String remoteUser = request.getRemoteUser();
        String queryString = request.getQueryString();
        String username = getUsername();
        RequestLog requestLog = new RequestLog();
        requestLog.setUsername(username);
        requestLog.setUrl(url);
        requestLog.setUri(uri);
        requestLog.setQueryString(queryString);
        requestLog.setMethod(method);
        requestLog.setIp(ip);
        requestLog.setRemoteUser(remoteUser);
        requestLog.setUa(UA.create(userAgent));
        log.info("{} 在 {} 开始访问 {} {} 参数:{}", username, ip, method, url, objectMapper.writeValueAsString(params));
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            requestLog.setMethodDef(handlerMethod.toString());
        }
        request.setAttribute("requestLog", requestLog);
        Instant startTime = Instant.now();
        request.setAttribute("startTime", startTime);
        return true;
    }

    /**
     * 获取用户名
     * @return
     */
    private String getUsername() {
        UserDetails userDetails = userAuditor.getCurrentUser();
        if (userDetails == null) {
            return "匿名用户";
        }
        return userDetails.getUsername();
    }

    /** 正常返回调用
     * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    /** 不管是否抛出异常都会调用
     * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        Instant startTime = (Instant) request.getAttribute("startTime");
        Instant endTime = Instant.now();
        long ms = Duration.between(startTime, endTime).toMillis();// 请求所耗费的时间 毫秒
        RequestLog requestLog = (RequestLog) request.getAttribute("requestLog");
        requestLog.setCostTime(ms);
        requestLog.setSuccess(ex == null);
        requestLogRepository.save(requestLog);
        ThreadLocalUtil.USER.remove(); // 清除当前线程用户引用，如不清除可能会造成内存泄漏
        log.info("{} 在 {} 访问 {} {} {}, 耗时 {}ms", requestLog.getUsername(), requestLog.getIp(), requestLog.getMethod(), requestLog.getUrl(), ex == null ? "成功" : "失败", ms);
    }

}
