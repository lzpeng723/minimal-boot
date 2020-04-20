package com.lzpeng.project.tool.config;

import com.lzpeng.project.tool.service.GenInitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 代码配置初始化配置
 * @date: 2020/4/12
 * @time: 22:58
 * @author:   李志鹏
 */
@Slf4j
@Component
public class GenInitialize implements ApplicationRunner {


    @Autowired
    private GenInitService genInitService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        genInitService.init();
    }
}
