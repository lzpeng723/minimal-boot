/**
 *
 */
package com.lzpeng.framework.web.config;

import com.lzpeng.framework.web.filter.TimeFilter;
import com.lzpeng.framework.web.interceptor.RequestLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;


/**
 * 过滤器，拦截器配置
 * @author 李志鹏
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@SuppressWarnings("unused")
	@Autowired
	private RequestLogInterceptor requestLogInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    // 拦截器注册器
		registry.addInterceptor(requestLogInterceptor)
				.excludePathPatterns("/**/webjars/**")
				.excludePathPatterns("/static/**")
				.excludePathPatterns("/swagger-resources/**")
				.excludePathPatterns("/error/**")
				.addPathPatterns("/**");
	}

	@Bean
	public FilterRegistrationBean timeFilter() {

		FilterRegistrationBean registrationBean = new FilterRegistrationBean();

		TimeFilter timeFilter = new TimeFilter();
		registrationBean.setFilter(timeFilter);

		List<String> urls = new ArrayList<>();
		urls.add("/**");
		registrationBean.setUrlPatterns(urls);

		return registrationBean;

	}


	/**
	 * 枚举类的转换器工厂 addConverterFactory
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(new IntegerToEnumConverterFactory());
		registry.addConverterFactory(new StringToIntEnumConverterFactory());
	}
}
