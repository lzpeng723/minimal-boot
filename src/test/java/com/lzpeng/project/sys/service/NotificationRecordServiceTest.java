package com.lzpeng.project.sys.service;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.project.sys.domain.NotificationRecord;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 通知记录 业务层单元测试
 *
 * @author : 李志鹏
 * @date : 2020-5-17
 * @time : 0:43:53
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class NotificationRecordServiceTest {

    @Autowired
    private NotificationRecordService notificationRecordService;

    @Test
    public void testSave() {
        NotificationRecord notificationRecord = new NotificationRecord();
        notificationRecord = notificationRecordService.save(notificationRecord);
        assertNotNull(notificationRecord.getId());
    }

    @Test
    public void testDelete() {
        String id = "{}";
        notificationRecordService.delete(id);
    }

    @Test
    public void testUpdate() {
        String id = "{}";
        NotificationRecord notificationRecord = new NotificationRecord();
        notificationRecord = notificationRecordService.update(id, notificationRecord);
        assertEquals(notificationRecord.getId(), id);
    }

    @Test
    public void testFindById() {
        String id = "{}";
        NotificationRecord notificationRecord = notificationRecordService.findById(id);
        assertEquals(notificationRecord.getId(), id);
    }

    @Test
    public void testQuery() {
        NotificationRecord notificationRecord = new NotificationRecord();
        QueryResult<NotificationRecord> result = notificationRecordService.query(0, 10, notificationRecord);
        assertNotNull(result.getList());
    }

    @Test
    public void testReadDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<NotificationRecord> notificationRecords = notificationRecordService.readDataFromJson(json);
        assertNotNull(notificationRecords);
        log.info("{}", notificationRecords);
    }

    @Test
    public void testImportDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<NotificationRecord> notificationRecords = notificationRecordService.importDataFromJson(json);
        assertNotNull(notificationRecords);
        log.info("{}", notificationRecords);
    }

}