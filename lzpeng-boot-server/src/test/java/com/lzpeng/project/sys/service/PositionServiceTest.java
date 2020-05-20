package com.lzpeng.project.sys.service;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.project.sys.domain.Position;
import com.lzpeng.project.sys.service.PositionService;
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
 * 岗位 业务层单元测试
 * @date: 2020-3-20
 * @time: 16:03:56
 * @author: 李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PositionServiceTest {

    @Autowired
    private PositionService positionService;

    @Test
    public void testSave() {
        Position position = new Position();
        position = positionService.save(position);
        assertNotNull(position.getId());
    }

    @Test
    public void testDelete() {
        String id = "{}";
        positionService.delete(id);
    }

    @Test
    public void testUpdate() {
        String id = "{}";
        Position position = new Position();
        position = positionService.update(id, position);
        assertEquals(position.getId(), id);
    }
    @Test
    public void testFindById() {
        String id = "{}";
        Position position = positionService.findById(id);
        assertEquals(position.getId(), id);
    }

    @Test
    public void testQuery() {
        Position position = new Position();
        QueryResult<Position> result = positionService.query(0, 10, position);
        assertNotNull(result.getList());
    }

    @Test
    public void testReadDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<Position> positions = positionService.readDataFromJson(json);
        assertNotNull(positions);
        log.info("{}", positions);
    }

    @Test
    public void testImportDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<Position> positions = positionService.importDataFromJson(json);
        assertNotNull(positions);
        log.info("{}", positions);
    }

}