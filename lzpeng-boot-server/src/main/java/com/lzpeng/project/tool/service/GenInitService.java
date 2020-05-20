package com.lzpeng.project.tool.service;

import cn.hutool.core.io.IoUtil;
import com.lzpeng.common.utils.EnvUtil;
import com.lzpeng.project.tool.domain.Gen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @date: 2020/4/20
 * @time: 20:54
 * @author: 李志鹏
 */
@Slf4j
@Service
public class GenInitService {

    /**
     * 代码配置初始化json
     */
    private Resource genData = new ClassPathResource("static/gen.json");

    @Autowired
    private GenService genService;

    /**
     * 初始化代码配置模板到数据库
     * @throws Exception
     */
    @Transactional(rollbackOn = Exception.class)
    public void init() throws Exception {
        // 如果是开发环境 删除所有模板
        if (EnvUtil.isDev()) {
            genService.deleteAll();
        }
        List<Gen> genList = genService.findAll();
        if (CollectionUtils.isEmpty(genList)) {
            genList = getInitGenList();
            //保存入库
            genService.saveAll(genList);
            log.info("初始化代码生成器配置成功");
        }
    }


    /**
     * 得到初始化代码配置模板
     * @return
     * @throws IOException
     */
    public List<Gen> getInitGenList() throws IOException {
        List<Gen> genList = genService.readDataFromJson(IoUtil.read(genData.getInputStream(), Charset.defaultCharset()));
        for (Gen gen : genList) {
            // 解析模板文件
            ClassPathResource templateData = new ClassPathResource(gen.getTemplate());
            gen.setTemplate(IoUtil.read(templateData.getInputStream(), Charset.defaultCharset()));
        }
        return genList;
    }
}
