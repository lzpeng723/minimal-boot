package com.lzpeng.project.sys.repository;

import com.lzpeng.project.sys.domain.NotificationRecord;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 通知记录 数据层单元测试
 *
 * @author : 李志鹏
 * @date : 2020-5-17
 * @time : 0:43:53
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class NotificationRecordRepositoryTest {

    @Autowired
    private NotificationRecordRepository notificationRecordRepository;

    @Test
    public void testSave() {
        NotificationRecord notificationRecord = new NotificationRecord();
        notificationRecord = notificationRecordRepository.save(notificationRecord);
        assertNotNull(notificationRecord.getId());
    }

    @Test
    public void testDelete() {
        String id = "{}";
        notificationRecordRepository.deleteById(id);
    }

    @Test
    public void testUpdate() {
        String id = "{}";
        Optional<NotificationRecord> optional = notificationRecordRepository.findById(id);
        optional.ifPresent(notificationRecord -> {
            notificationRecord = notificationRecordRepository.save(notificationRecord);
            assertEquals(notificationRecord.getId(), id);
        });
    }

    @Test
    public void testFindAll() {
        Page<NotificationRecord> notificationRecordPage = notificationRecordRepository.findAll(PageRequest.of(0, 10));
        assertNotNull(notificationRecordPage.getContent());
    }

    @Test
    public void testFindById() {
        String id = "{}";
        Optional<NotificationRecord> optional = notificationRecordRepository.findById(id);
        NotificationRecord notificationRecord = optional.orElse(null);
        assertNotNull(notificationRecord);
    }
}