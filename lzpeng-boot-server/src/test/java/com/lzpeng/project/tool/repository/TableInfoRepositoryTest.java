package com.lzpeng.project.tool.repository;

import com.lzpeng.project.tool.domain.TableInfo;
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
 * 表信息 数据层单元测试
 * @date: 2020-4-16
 * @time: 11:47:29
 * @author: 李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TableInfoRepositoryTest {

    @Autowired
    private TableInfoRepository tableInfoRepository;

    @Test
    public void testSave(){
        TableInfo tableInfo = new TableInfo();
        tableInfo = tableInfoRepository.save(tableInfo);
        assertNotNull(tableInfo.getId());
    }

    @Test
    public void testDelete(){
        String id = "{}";
        tableInfoRepository.deleteById(id);
    }

    @Test
    public void testUpdate(){
        String id = "{}";
        Optional<TableInfo> optional = tableInfoRepository.findById(id);
        optional.ifPresent(tableInfo -> {
            tableInfo = tableInfoRepository.save(tableInfo);
            assertEquals(tableInfo.getId(), id);
        });
    }

    @Test
    public void testFindAll(){
        Page<TableInfo> tableInfoPage = tableInfoRepository.findAll(PageRequest.of(0, 10));
        assertNotNull(tableInfoPage.getContent());
    }

    @Test
    public void testFindById(){
        String id = "{}";
        Optional<TableInfo> optional = tableInfoRepository.findById(id);
        TableInfo tableInfo = optional.orElse(null);
        assertNotNull(tableInfo);
    }
}