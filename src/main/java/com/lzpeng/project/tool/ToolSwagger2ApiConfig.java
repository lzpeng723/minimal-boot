package com.lzpeng.project.tool;

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
public class ToolSwagger2ApiConfig {
    @Bean
    public Docket toolApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统工具")
                .apiInfo(apiInfo("系统工具API", "系统工具API", "1.0"))
                //.globalOperationParameters(parameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName()))
                .paths(PathSelectors.any())
                .build();
    }
}
