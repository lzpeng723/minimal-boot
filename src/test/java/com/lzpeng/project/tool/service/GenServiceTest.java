package com.lzpeng.project.tool.service;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.project.tool.domain.Gen;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 代码生成模板 业务层单元测试
 * @date: 2020-4-16
 * @time: 11:47:29
 * @author: 李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class GenServiceTest {

    @Autowired
    private GenInitService genInitService;

    @Autowired
    private GenService genService;

    @Test
    public void testSave() {
        Gen gen = new Gen();
        gen = genService.save(gen);
        assertNotNull(gen.getId());
    }

    @Test
    public void testDelete() {
        String id = "{}";
        genService.delete(id);
    }

    @Test
    public void testUpdate() {
        String id = "{}";
        Gen gen = new Gen();
        gen = genService.update(id, gen);
        assertEquals(gen.getId(), id);
    }
    @Test
    public void testFindById() {
        String id = "{}";
        Gen gen = genService.findById(id);
        assertEquals(gen.getId(), id);
    }

    @Test
    public void testQuery() {
        Gen gen = new Gen();
        QueryResult<Gen> result = genService.query(0, 10, gen);
        assertNotNull(result.getList());
    }

    @Test
    public void testReadDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<Gen> gens = genService.readDataFromJson(json);
        assertNotNull(gens);
        log.info("{}", gens);
    }

    @Test
    public void testImportDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<Gen> gens = genService.importDataFromJson(json);
        assertNotNull(gens);
        log.info("{}", gens);
    }

    @Test
    public void testGenCodeToUserDir() throws IOException {
        //genService.genCodeToUserDir(ReportTree.class);
    }

}