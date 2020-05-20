/**
 * 
 */
package com.lzpeng.framework.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * 在 Filter 中并不知道这个请求最终是由哪个方法处理的 可以通过拦截器获取
 * 过滤器实现请求计时
 * 先进过滤器 再进拦截器 接下来进入AOP
 * @author 李志鹏
 *
 */
@Slf4j
//@Component
public class TimeFilter implements Filter {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		log.info("time filter destroy");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("time filter start");
		long start = System.currentTimeMillis();
		chain.doFilter(request, response);
		log.info("time filter 耗时:"+ (System.currentTimeMillis() - start));
		log.info("time filter finish");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		log.info("time filter init");
	}

}
