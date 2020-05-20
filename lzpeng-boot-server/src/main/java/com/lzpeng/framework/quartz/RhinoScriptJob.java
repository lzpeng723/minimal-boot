package com.lzpeng.framework.quartz;

import com.lzpeng.common.utils.RhinoUtil;
import lombok.extern.slf4j.Slf4j;
import org.mozilla.javascript.Context;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.HashMap;
import java.util.Map;

/**
 * // 内置变量 log: 打印后台日志,类型 org.slf4j.Logger
 * // 内置变量 jobKey: 定时任务标识,类型 org.quartz.JobKey
 * // 内置变量 jobDetail: 定时任务对象,类型 org.quartz.JobDetail
 * // 内置变量 jobDataMap: 定时任务参数,类型 org.quartz.JobDataMap
 * // 内置变量 jobExecutionContext: 定时任务参数,类型 org.quartz.JobDataMap
 * // 内置变量 jobExecutionContext: 定时任务执行时的上下文,类型 org.quartz.JobExecutionContext
 * // 内置变量 jobDataMap.getString("script"): 定时任务执行的脚本,类型 java.lang.String
 * // 内置变量 jobDataMap.get("job"): 定时任务配置,类型 com.lzpeng.project.monitor.domain.Job
 * var imp = JavaImporter();
 * imp.importPackage(Packages.java.lang);
 * imp.importPackage(Packages.java.util);
 * imp.importPackage(Packages.cn.hutool.extra.spring);
 * with (imp) {
 *      // 可以自定义函数
 *      function getBean(beanName){
 * 		    return SpringUtil.getBean(beanName);
 *      }
 *     var jobService = getBean("jobService"); // 获取Bean
 *     var job = jobDataMap.get("job"); // 获取当前定时任务配置
 *     log.info("jobKey: {}", jobKey); // 获取当前jobKey
 *     log.info("jobService: {}", jobService.getClass()); // Bean jobService
 *     log.info("jobExecutionContext: {}", jobExecutionContext); // 获取当前jobExecutionContext
 * }
 * 动态定时任务
 * TODO 支持 SPEL,JS,Rhino 脚本
 * @date: 2020/4/6
 * @time: 21:14
 * @author:   李志鹏
 */
@Slf4j
@DisallowConcurrentExecution
public class RhinoScriptJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        log.info("开始执行 {} 任务", jobKey);
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String script = jobDataMap.getString("script");
        Object result = RhinoUtil.execute(jobKey.toString(), script, buildMap(jobExecutionContext));
        log.info("Rhino 脚本返回值:{} {}", result.getClass(), Context.toString(result));
    }

    /**
     * 构建需要向Rhino脚本中传递的java对象
     * @param jobExecutionContext
     * @return
     */
    private Map<String, Object> buildMap(JobExecutionContext jobExecutionContext) {
        Map<String, Object> map = new HashMap<>();
        map.put("log", log);
        if (jobExecutionContext != null) {
            JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
            JobDetail jobDetail = jobExecutionContext.getJobDetail();
            JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
            map.put("jobKey", jobKey);
            map.put("jobDetail", jobDetail);
            map.put("jobDataMap", jobDataMap);
            map.put("jobExecutionContext", jobExecutionContext);
        }
        return map;
    }
}
