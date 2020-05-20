package com.lzpeng.framework;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 用户 业务层
 * @date: 2020/2/1
 * @time: 23:44
 * @author: 李志鹏
 * @EnableSwagger2 扫描 标记 org.springframework.web.bind.annotation.RestController 的所有类的接口文档
 * @Import(SpringDataRestConfiguration.class) // 扫描 data rest 接口
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@ConditionalOnMissingClass("org.junit.Test")
public class Swagger2Configuration {

    public static ApiInfo apiInfo(String title, String description, String version) {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
//                .termsOfServiceUrl("/")
                .version(version)
                .build();
    }

}
