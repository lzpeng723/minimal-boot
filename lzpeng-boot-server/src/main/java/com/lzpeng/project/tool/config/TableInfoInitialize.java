package com.lzpeng.project.tool.config;

import com.lzpeng.project.tool.service.TableInfoInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 数据字典初始化
 * @date: 2020/3/18
 * @time: 19:04
 * @author:   李志鹏
 */
@Configuration
public class TableInfoInitialize {

    @Autowired
    private TableInfoInitService tableInfoInitService;

    /**
     * 初始化数据字典
     * @return
     */
    @Bean
    public ApplicationRunner initTableInfo(){
        return args -> tableInfoInitService.initTableInfo();
    }
}
