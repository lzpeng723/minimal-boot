package com.lzpeng;

import cn.hutool.extra.spring.SpringUtil;
import com.lzpeng.common.utils.EnvUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 主模块模块启动类
 * @date: 2020-2-23
 * @time: 12:37:20
 * @author: 李志鹏
 */
@Slf4j
@EnableCaching
@EnableJpaAuditing
@Import(SpringUtil.class)
@SpringBootApplication(exclude = FreeMarkerAutoConfiguration.class)
public class MainApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        // 加载 Spring 版本 并控制台打印
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(MainApplication.class)
                .main(SpringVersion.class)
                .bannerMode(Banner.Mode.CONSOLE)
                .run(args);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        Banner banner = applicationContext.getBean(Banner.class);
        banner.printBanner(environment, SpringVersion.class, System.out);
        // 退出登录
        SecurityContextHolder.getContext().setAuthentication(null);
        int port = environment.getProperty("server.port", int.class, 8080);
        long endTime = System.currentTimeMillis();
        log.info("极简后台管理系统在 {} 端口启动成功, 耗时 {}s", port, (endTime - startTime)/1e3);
        log.info("当前环境是 {}", EnvUtil.isDev() ? "开发环境" : "正式环境");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainApplication.class);
    }

}
