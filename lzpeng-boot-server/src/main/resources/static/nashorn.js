// Alt + / 提示代码
// 内置变量 log: 打印后台日志,类型 org.slf4j.Logger
// 内置变量 jobKey: 定时任务标识,类型 org.quartz.JobKey
// 内置变量 jobDetail: 定时任务对象,类型 org.quartz.JobDetail
// 内置变量 jobDataMap: 定时任务参数,类型 org.quartz.JobDataMap
// 内置变量 jobExecutionContext: 定时任务执行时的上下文,类型 org.quartz.JobExecutionContext
// 内置变量 jobDataMap.getString("script"): 定时任务执行的脚本,类型 java.lang.String
// 内置变量 jobDataMap.get("jobId"): 定时任务Id,类型 java.lang.String
var imp = JavaImporter(
    Packages.java.lang,
    Packages.java.util,
    Packages.cn.hutool.extra.spring
);
with (imp) {
    // 可以自定义函数
    function getBean(beanName){
        return SpringUtil.getBean(beanName);
    }
    var jobService = getBean("jobService"); // 获取Bean
    var jobId = jobDataMap.getString("jobId"); // 获取当前定时任务id
    var job = jobService.findById(jobId); // 获取当前定时任务配置
    log.info("jobType: {}", job.getType()); // 获取当前定时任务类型
    log.info("jobKey: {}", jobKey); // 获取当前定时任务标识
    log.info("jobService: {}", jobService.getClass()); // Bean jobService
    log.info("jobExecutionContext: {}", jobExecutionContext); // 获取当前定时任务上下文
}