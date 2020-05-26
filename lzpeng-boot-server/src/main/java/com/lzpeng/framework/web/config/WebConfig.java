/**
 *
 */
package com.lzpeng.framework.web.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.lzpeng.framework.web.filter.TimeFilter;
import com.lzpeng.framework.web.interceptor.RequestLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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

	/**
	 * 序列化使用转换器
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		ObjectMapper mapper = converter.getObjectMapper();
		Hibernate5Module hibernate5Module = new Hibernate5Module();
		// 禁用 {@link javax.persistence.Transient} 检验,使其仍可被序列化到前台
		hibernate5Module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
		// Spring Boot JPA Entity Jackson序列化触发懒加载的解决方案 https://www.cnblogs.com/ymstars/p/10473425.html
		 mapper.registerModule(hibernate5Module);
		// 忽略空属性
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// https://blog.csdn.net/J080624/article/details/82529082
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		return converter;
	}
}
