package com.lzpeng.project.sys.repository;

import com.lzpeng.project.sys.domain.Notice;
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
 * 通知 数据层单元测试
 *
 * @author : 李志鹏
 * @date : 2020-5-16
 * @time : 21:03:56
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    public void testSave() {
        Notice notice = new Notice();
        notice = noticeRepository.save(notice);
        assertNotNull(notice.getId());
    }

    @Test
    public void testDelete() {
        String id = "{}";
        noticeRepository.deleteById(id);
    }

    @Test
    public void testUpdate() {
        String id = "{}";
        Optional<Notice> optional = noticeRepository.findById(id);
        optional.ifPresent(notice -> {
            notice = noticeRepository.save(notice);
            assertEquals(notice.getId(), id);
        });
    }

    @Test
    public void testFindAll() {
        Page<Notice> noticePage = noticeRepository.findAll(PageRequest.of(0, 10));
        assertNotNull(noticePage.getContent());
    }

    @Test
    public void testFindById() {
        String id = "{}";
        Optional<Notice> optional = noticeRepository.findById(id);
        Notice notice = optional.orElse(null);
        assertNotNull(notice);
    }
}