package com.lzpeng.project.home.service;

import cn.hutool.json.JSONObject;
import com.lzpeng.framework.util.PermissionUtil;
import com.lzpeng.project.monitor.service.RequestLogService;
import com.lzpeng.project.sys.domain.NotificationRecord;
import com.lzpeng.project.sys.domain.QNotificationRecord;
import com.lzpeng.project.sys.domain.User;
import com.lzpeng.project.sys.service.NotificationRecordService;
import com.lzpeng.project.sys.service.UserService;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 首页 Service
 * @date: 2020/5/24
 * @time: 0:40
 * @author: 李志鹏
 */
@Service
public class DashboardService {

    /**
     * 用户 Service
     */
    @Autowired
    private UserService userService;

    /**
     * 通知记录 Service
     */
    @Autowired
    private NotificationRecordService notificationRecordService;

    /**
     * 请求日志 Service
     */
    @Autowired
    private RequestLogService requestLogService;


    /**
     * 返回首页数据
     * @return
     */
    public Map<String, Object> index() {
        if (PermissionUtil.hasRole("USER")) {
            return userIndex();
        } else if (PermissionUtil.hasRole("ADMIN")) {
            return adminIndex();
        }
        return null;
    }

    /**
     * 获取通知
     * @return
     */
    public List<NotificationRecord> notificationRecords(){
        if (PermissionUtil.hasRole("USER")) {
            QNotificationRecord notificationRecord = QNotificationRecord.notificationRecord;
            // 获取当前用户的通知
            User user = userService.getCurrentUser();
            BooleanExpression predicate = notificationRecord.receiver.id.eq(user.getId());
            predicate.and(notificationRecord.view.eq(false));
            return notificationRecordService.findAll(predicate, Sort.by(Sort.Direction.ASC, "view"));
        } else if (PermissionUtil.hasRole("ADMIN")) {
            return notificationRecordService.findAll(Sort.by(Sort.Direction.ASC, "view"));
        }
        return Arrays.asList();
    }
    /**
     * 管理员首页数据
     * @return
     */
    private Map<String, Object> adminIndex() {
        JSONObject obj = new JSONObject();
        obj.set("userCount", userService.count());
        obj.set("noticeCount", notificationRecordService.count());
        obj.set("requestCount", requestLogService.count());
        obj.set("todoListCount", 0);
        return obj;
    }

    /**
     * 普通用户首页数据
     * @return
     */
    private Map<String, Object> userIndex() {
        JSONObject obj = new JSONObject();
        User user = userService.getCurrentUser();
        QNotificationRecord notificationRecord = QNotificationRecord.notificationRecord;
        // 接收者是当前用户的通知数
        BooleanExpression predicate = notificationRecord.receiver.id.eq(user.getId());
        obj.set("noticeCount", notificationRecordService.count(predicate));
        obj.set("todoListCount", 0);
        return obj;
    }



}
