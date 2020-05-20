package com.lzpeng.project.sys.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

/**
 * 通知记录 业务层
 *
 * @author : 李志鹏
 * @date : 2020-5-17
 * @time : 0:43:53
 */
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class NotificationRecordService extends AbstractNotificationRecordService {

}
