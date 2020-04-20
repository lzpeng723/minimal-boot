package com.lzpeng.framework.quartz;

import cn.hutool.extra.spring.SpringUtil;
import com.lzpeng.project.monitor.domain.Job;
import com.lzpeng.project.monitor.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Java 定时任务示例
 * @date: 2020/4/6
 * @time: 16:21
 * @author:   李志鹏
 */
@Slf4j
@DisallowConcurrentExecution
public class SampleJavaJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        log.info("开始执行 {} 任务", jobKey);
        JobService jobService = SpringUtil.getBean("jobService"); // 获取Bean
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String jobId = jobDataMap.getString("jobId");
        Job job = jobService.findById(jobId); // 获取当前定时任务配置
        log.info("jobType: {}", job.getType()); // 获取当前定时任务类型
        log.info("jobKey: {}", jobKey); // 获取当前定时任务标识
        log.info("jobService: {}", jobService.getClass()); // Bean jobService
        log.info("jobExecutionContext: {}", jobExecutionContext); // 获取当前定时任务上下文
    }
}
