package com.lzpeng.project.sys;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static com.lzpeng.framework.Swagger2Configuration.apiInfo;

/**
 * 系统管理模块模块启动类
 * @date: 2020-3-6
 * @time: 20:45:53
 * @author: 李志鹏
 */
@Configuration
public class SysSwagger2ApiConfig {

    @Bean
    public Docket sysApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统管理")
                .apiInfo(apiInfo("系统管理API", "系统管理API", "1.0"))
                .select()
                .apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName()))
                .paths(PathSelectors.any())
                .build();
    }


}
