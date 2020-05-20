package com.lzpeng.project.tool.service;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.project.tool.domain.TableInfo;
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
 * 表信息 业务层单元测试
 * @date: 2020-4-16
 * @time: 11:47:29
 * @author: 李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TableInfoServiceTest {

    @Autowired
    private TableInfoService tableInfoService;

    @Test
    public void testSave() {
        TableInfo tableInfo = new TableInfo();
        tableInfo = tableInfoService.save(tableInfo);
        assertNotNull(tableInfo.getId());
    }

    @Test
    public void testDelete() {
        String id = "{}";
        tableInfoService.delete(id);
    }

    @Test
    public void testUpdate() {
        String id = "{}";
        TableInfo tableInfo = new TableInfo();
        tableInfo = tableInfoService.update(id, tableInfo);
        assertEquals(tableInfo.getId(), id);
    }
    @Test
    public void testFindById() {
        String id = "{}";
        TableInfo tableInfo = tableInfoService.findById(id);
        assertEquals(tableInfo.getId(), id);
    }

    @Test
    public void testQuery() {
        TableInfo tableInfo = new TableInfo();
        QueryResult<TableInfo> result = tableInfoService.query(0, 10, tableInfo);
        assertNotNull(result.getList());
    }

    @Test
    public void testReadDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<TableInfo> tableInfos = tableInfoService.readDataFromJson(json);
        assertNotNull(tableInfos);
        log.info("{}", tableInfos);
    }

    @Test
    public void testImportDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<TableInfo> tableInfos = tableInfoService.importDataFromJson(json);
        assertNotNull(tableInfos);
        log.info("{}", tableInfos);
    }

}