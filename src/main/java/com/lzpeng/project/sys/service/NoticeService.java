package com.lzpeng.project.sys.service;

import com.lzpeng.project.sys.domain.Notice;
import com.lzpeng.project.sys.domain.NotificationRecord;
import com.lzpeng.project.sys.domain.QNotificationRecord;
import com.lzpeng.project.sys.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 通知 业务层
 *
 * @author : 李志鹏
 * @date : 2020-5-16
 * @time : 21:03:56
 */
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class NoticeService extends AbstractNoticeService {

    /**
     * 通知记录 Service
     */
    @Autowired
    private NotificationRecordService notificationRecordService;

    /**
     * 用户Service
     */
    @Autowired
    private UserService userService;


    /**
     * 发送通知
     *
     * @param noticeId    通知id
     * @param receiverIds 接收人id数组
     * @return
     */
    public void sendNotice(String noticeId, String[] receiverIds) {
        User sender = userService.getCurrentUser();
        Notice notice = findById(noticeId);
        List<NotificationRecord> notificationRecordList = new ArrayList<>();
        if (receiverIds != null && receiverIds.length > 0) {
            for (String receiverId : receiverIds) {
                User receiver = userService.findById(receiverId);
                NotificationRecord notificationRecord = new NotificationRecord();
                notificationRecord.setSender(sender);
                notificationRecord.setNotice(notice);
                notificationRecord.setReceiver(receiver);
                notificationRecordList.add(notificationRecord);
            }
        }
        notificationRecordService.saveAll(notificationRecordList);
    }

    /**
     * 返回 接收人是自己的所有 通知消息
     *
     * @return
     */
    public List<NotificationRecord> receiveNotice() {
        User me = userService.getCurrentUser();
        List<NotificationRecord> notificationRecords = notificationRecordService.findAll(
                QNotificationRecord.notificationRecord
                        .receiver.id.eq(me.getId()));
        return notificationRecords;
    }
}
