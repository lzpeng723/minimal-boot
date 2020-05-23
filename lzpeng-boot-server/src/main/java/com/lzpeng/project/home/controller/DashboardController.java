package com.lzpeng.project.home.controller;

import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.project.home.service.DashboardService;
import com.lzpeng.project.sys.domain.NotificationRecord;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 首页数据 Controller
 * @date: 2020/5/24
 * @time: 0:27
 * @author: 李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/home/dashboard")
@Api(tags = "首页", value = "获取首页数据")
public class DashboardController {
    /**
     * 首页服务Service
     */
    @Autowired
    private DashboardService dashboardService;

    /**
     * 返回首页数据
     * @return
     */
    @GetMapping("/count")
    public Result count(){
        Map<String, Object> map = dashboardService.index();
        return ResultUtil.success(map);
    }
    /**
     * 返回通知列表
     * @return
     */
    @GetMapping("/notice")
    public Result notificationRecords(){
        List<NotificationRecord> notificationRecordList = dashboardService.notificationRecords();
        return ResultUtil.success(notificationRecordList);
    }

}
