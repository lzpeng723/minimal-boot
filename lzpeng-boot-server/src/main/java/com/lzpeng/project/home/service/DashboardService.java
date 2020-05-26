package com.lzpeng.project.home.service;

import cn.hutool.json.JSONObject;
import com.lzpeng.project.monitor.service.RequestLogService;
import com.lzpeng.project.sys.domain.*;
import com.lzpeng.project.sys.service.NotificationRecordService;
import com.lzpeng.project.sys.service.UserService;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    /**
     * 返回首页数据
     * @return
     */
    public Map<String, Object> index() {
        User user = userService.getCurrentUser();
        Set<String> roleSet = user.getRoles().stream().map(Role::getNumber).collect(Collectors.toSet());
        if (roleSet.contains("USER")) {
            return userIndex(user);
        } else if (roleSet.contains("ADMIN")) {
            return adminIndex();
        }
        return null;
    }

    /**
     * 获取通知
     * @return
     */
    public List<NotificationRecord> notificationRecords(){
        User user = userService.getCurrentUser();
        Set<String> roleSet = user.getRoles().stream().map(Role::getNumber).collect(Collectors.toSet());
        if (roleSet.contains("USER")) {
            QNotificationRecord notificationRecord = QNotificationRecord.notificationRecord;
            // 接收者是当前用户的通知数
            BooleanExpression predicate = notificationRecord.receiver.id.eq(user.getId());
            return notificationRecordService.findAll(predicate);
//            List results = jpaQueryFactory.select(notificationRecord.id, notificationRecord.notice.id, notificationRecord.notice.name, notificationRecord.sender.id, notificationRecord.sender.username, notificationRecord.createTime)
//                    .from(notificationRecord)
//                    .where(predicate)
//                    .fetchResults()
//                    .getResults();
//            return results;
        } else if (roleSet.contains("ADMIN")) {
            return notificationRecordService.findAll();
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
     * @param user 当前用户
     * @return
     */
    private Map<String, Object> userIndex(User user) {
        JSONObject obj = new JSONObject();
        QNotificationRecord notificationRecord = QNotificationRecord.notificationRecord;
        // 接收者是当前用户的通知数
        BooleanExpression predicate = notificationRecord.receiver.id.eq(user.getId());
        obj.set("noticeCount", notificationRecordService.count(predicate));
        obj.set("todoListCount", 0);
        return obj;
    }



}
