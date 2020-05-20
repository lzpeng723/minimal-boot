package com.lzpeng.project.monitor.service;

import cn.hutool.core.util.RandomUtil;
import com.lzpeng.framework.quartz.NashornScriptJob;
import com.lzpeng.framework.quartz.RhinoScriptJob;
import com.lzpeng.project.monitor.domain.Job;
import com.lzpeng.project.monitor.domain.JobStatus;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

/**
 * 定时任务 业务层
 * @date: 2020-4-6
 * @time: 17:39:39
 * @author: 李志鹏
 * @see {https://gitee.com/hengboy/spring-boot-chapter/blob/2.x/Chapter47/src/main/java/com/hengyu/chapter39/good/service/GoodInfoService.java}
 */
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class JobService extends AbstractJobService {

    /**
     * 注入任务调度器
     */
    @Autowired
    private Scheduler scheduler;

    /**
     * 立即执行一次定时任务
     * @param jobId 任务id
     */
    public void executeJob(String jobId) throws SchedulerException{
        Job job = findById(jobId);
        JobKey jobKey = JobKey.jobKey(job.getName(), job.getGroup());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            jobDetail = getJobDetail(job);
            String temp = RandomUtil.randomString(6);
            TriggerKey tempTriggerKey = TriggerKey.triggerKey(temp, temp);
            Trigger tempTrigger = TriggerBuilder.newTrigger().withIdentity(tempTriggerKey).startNow().build();
            scheduler.scheduleJob(jobDetail, tempTrigger); // 使用临时触发器触发任务
        } else {
            // 当前已启动
            scheduler.triggerJob(jobKey, jobDetail.getJobDataMap()); // 直接执行
        }
    }

    /**
     * 注册定时任务
     * @param jobId 任务id
     */
    @CacheEvict(value = ENTITY_NAME, key = "#jobId")
    public void registerJob(String jobId) throws SchedulerException {
        Job job = findById(jobId);
        registerJob(job);
        jobRepository.updateJobStatus(jobId, JobStatus.RUNNING);
    }
    /**
     * 注册定时任务
     * @param job 任务
     */
    private void registerJob(Job job) throws SchedulerException {
        String name = job.getName(); //任务名称
        String group = job.getGroup();  //任务所属分组
        JobKey jobKey = JobKey.jobKey(name, group); // jobKey
        if (scheduler.getJobDetail(jobKey) != null) {
            log.error("{}.{} {} 定时任务已创建", group, name, job.getClazz());
            return;
        }
        JobDetail jobDetail = getJobDetail(job);
        // 触发时机
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
        //创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(scheduleBuilder).build();
        //将触发器与任务绑定到调度器内
        scheduler.scheduleJob(jobDetail, trigger);
        log.info("创建定时任务成功: {}.{} {}", group, name, job.getClazz());
    }

    /**
     * 获取任务对象
     * @param job
     * @return
     */
    private JobDetail getJobDetail(Job job) {
        String name = job.getName(); //任务名称
        String group = job.getGroup();  //任务所属分组
        JobKey jobKey = JobKey.jobKey(name, group); // jobKey
        //创建任务
        JobDetail jobDetail;
        switch (job.getType()) {
            case JAVA:
                jobDetail = JobBuilder.newJob(job.getClazz())
                        .withIdentity(jobKey)
                        .usingJobData("jobId", job.getId())
                        .build();
                break;
            case RHINO:
                // Rhino 脚本任务
                jobDetail = JobBuilder.newJob(RhinoScriptJob.class)
                        .withIdentity(jobKey)
                        .usingJobData("jobId", job.getId())
                        .usingJobData("script", job.getScript())
                        .build();
                break;
            case NASHORN:
                // Nashorn 脚本任务
                jobDetail = JobBuilder.newJob(NashornScriptJob.class)
                        .withIdentity(jobKey)
                        .usingJobData("jobId", job.getId())
                        .usingJobData("script", job.getScript())
                        .build();
                break;
            default:
                log.error("创建定时任务出错: {}.{} {}", group, name, job.getClazz());
                throw new RuntimeException("创建定时任务出错");
        }
        return jobDetail;
    }

    /**
     * 注册定时任务
     * @param jobs 任务集合
     */
    public void registerJob(Collection<Job> jobs) throws SchedulerException {
        for (Job job : jobs) {
            registerJob(job);
        }
    }

    /**
     * 暂停任务
     * @param jobId 任务id
     * @throws SchedulerException
     */
    @CacheEvict(value = ENTITY_NAME, key = "#jobId")
    public void pauseJob(String jobId) throws SchedulerException {
        Job job = findById(jobId);
        pauseJob(job);
        jobRepository.updateJobStatus(jobId, JobStatus.PAUSE);
    }
    /**
     * 暂停任务
     * @param job 任务
     * @throws SchedulerException
     */
    private void pauseJob(Job job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getName(), job.getGroup());
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(), job.getGroup());
        scheduler.pauseJob(jobKey);
        scheduler.pauseTrigger(triggerKey);
    }
    /**
     * 恢复任务
     * @param jobId 任务id
     * @throws SchedulerException
     */
    @CacheEvict(value = ENTITY_NAME, key = "#jobId")
    public void resumeJob(String jobId) throws SchedulerException {
        Job job = findById(jobId);
        resumeJob(job);
        jobRepository.updateJobStatus(jobId, JobStatus.RUNNING);
    }

    /**
     * 恢复任务
     * @param job 任务
     * @throws SchedulerException
     */
    private void resumeJob(Job job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getName(), job.getGroup());
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(), job.getGroup());
        scheduler.resumeJob(jobKey);
        scheduler.resumeTrigger(triggerKey);
    }

    /**
     * 停止任务
     * @param jobId 任务id
     * @throws SchedulerException
     */
    @CacheEvict(value = ENTITY_NAME, key = "#jobId")
    public void stopJob(String jobId) throws SchedulerException {
        Job job = findById(jobId);
        JobKey jobKey = JobKey.jobKey(job.getName(), job.getGroup());
        scheduler.deleteJob(jobKey);
        jobRepository.updateJobStatus(jobId, JobStatus.STOP);
    }

    /**
     * 初始化定时任务
     * @throws SchedulerException
     */
    public void initJob() throws SchedulerException {
        List<Job> jobList = findAll();
        for (Job job : jobList) {
            if (JobStatus.STOP.equals(job.getJobStatus()) || job.getJobStatus() == null) {
                // 定时任务是停止状态
                continue;
            }
            // 注册任务
            registerJob(job);
            if (JobStatus.PAUSE.equals(job.getJobStatus())) {
                // 定时任务是暂停状态
                pauseJob(job);
            }
        }
        scheduler.start();
    }


}
