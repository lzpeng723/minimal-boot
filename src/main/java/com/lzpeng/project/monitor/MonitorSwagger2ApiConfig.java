package com.lzpeng.project.monitor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static com.lzpeng.framework.Swagger2Configuration.apiInfo;

/**
 * @date: 2020/3/6
 * @time: 9:34
 * @author: 李志鹏
 */
@Configuration
public class MonitorSwagger2ApiConfig {
    @Bean
    public Docket monitorApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统监控")
                .apiInfo(apiInfo("系统监控API", "系统监控API", "1.0"))
                //.globalOperationParameters(parameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName()))
                .paths(PathSelectors.any())
                .build();
    }
}
