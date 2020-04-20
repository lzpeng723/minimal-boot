package com.lzpeng.project.monitor.config;

import cn.hutool.core.io.IoUtil;
import com.lzpeng.project.monitor.domain.Job;
import com.lzpeng.project.monitor.domain.JobType;
import com.lzpeng.project.monitor.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 定时任务初始化配置
 * @date: 2020/4/9
 * @time: 22:58
 * @author:   李志鹏
 */
@Slf4j
@Component
public class JobInitialize implements ApplicationRunner {


    /**
     * job初始化json
     */
    private Resource jobData = new ClassPathResource("static/job.json");

    @Autowired
    private JobService jobService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void run(ApplicationArguments args) throws Exception {
        List<Job> jobList = jobService.findAll();
        if (CollectionUtils.isEmpty(jobList)) {
            jobList = jobService.readDataFromJson(IoUtil.read(jobData.getInputStream(), Charset.defaultCharset()));
            for (Job job : jobList) {
                // 不是Java 解析脚本
                if (!job.getType().equals(JobType.JAVA)) {
                    ClassPathResource scriptData = new ClassPathResource(job.getScript());
                    job.setScript(IoUtil.read(scriptData.getInputStream(), Charset.defaultCharset()));
                }
            }
            jobService.saveAll(jobList);//保存入库
            log.info("初始化定时任务成功");
        } else {
            jobService.initJob();
            log.info("初始化定时任务成功");
        }
    }

}
