package com.lzpeng.project.sys.service;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.project.sys.domain.Notice;
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
 * 通知 业务层单元测试
 *
 * @author : 李志鹏
 * @date : 2020-5-16
 * @time : 21:03:56
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class NoticeServiceTest {

    @Autowired
    private NoticeService noticeService;

    @Test
    public void testSave() {
        Notice notice = new Notice();
        notice = noticeService.save(notice);
        assertNotNull(notice.getId());
    }

    @Test
    public void testDelete() {
        String id = "{}";
        noticeService.delete(id);
    }

    @Test
    public void testUpdate() {
        String id = "{}";
        Notice notice = new Notice();
        notice = noticeService.update(id, notice);
        assertEquals(notice.getId(), id);
    }

    @Test
    public void testFindById() {
        String id = "{}";
        Notice notice = noticeService.findById(id);
        assertEquals(notice.getId(), id);
    }

    @Test
    public void testQuery() {
        Notice notice = new Notice();
        QueryResult<Notice> result = noticeService.query(0, 10, notice);
        assertNotNull(result.getList());
    }

    @Test
    public void testReadDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<Notice> notices = noticeService.readDataFromJson(json);
        assertNotNull(notices);
        log.info("{}", notices);
    }

    @Test
    public void testImportDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<Notice> notices = noticeService.importDataFromJson(json);
        assertNotNull(notices);
        log.info("{}", notices);
    }

}