package com.lzpeng.project.${moduleName}.service;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lzpeng.common.response.QueryResult;
import ${fullClassName};
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
 * ${chineseClassName} 业务层单元测试
 * @date : ${.now?date}
 * @time : ${.now?time}
 * @author : 李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ${simpleClassName}ServiceTest {

    @Autowired
    private ${simpleClassName}Service ${simpleClassName?uncap_first}Service;

    @Test
    public void testSave() {
        ${simpleClassName} ${simpleClassName?uncap_first} = new ${simpleClassName}();
        ${simpleClassName?uncap_first} = ${simpleClassName?uncap_first}Service.save(${simpleClassName?uncap_first});
        assertNotNull(${simpleClassName?uncap_first}.getId());
    }

    @Test
    public void testDelete() {
        String id = "{}";
        ${simpleClassName?uncap_first}Service.delete(id);
    }

    @Test
    public void testUpdate() {
        String id = "{}";
        ${simpleClassName} ${simpleClassName?uncap_first} = new ${simpleClassName}();
        ${simpleClassName?uncap_first} = ${simpleClassName?uncap_first}Service.update(id, ${simpleClassName?uncap_first});
        assertEquals(${simpleClassName?uncap_first}.getId(), id);
    }
    @Test
    public void testFindById() {
        String id = "{}";
        ${simpleClassName} ${simpleClassName?uncap_first} = ${simpleClassName?uncap_first}Service.findById(id);
        assertEquals(${simpleClassName?uncap_first}.getId(), id);
    }

    @Test
    public void testQuery() {
        ${simpleClassName} ${simpleClassName?uncap_first} = new ${simpleClassName}();
        QueryResult<${simpleClassName}> result = ${simpleClassName?uncap_first}Service.query(0, 10, ${simpleClassName?uncap_first});
        assertNotNull(result.getList());
    }

    @Test
    public void testReadDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<${simpleClassName}> ${simpleClassName?uncap_first}s = ${simpleClassName?uncap_first}Service.readDataFromJson(json);
        assertNotNull(${simpleClassName?uncap_first}s);
        log.info("{}", ${simpleClassName?uncap_first}s);
    }

    @Test
    public void testImportDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<${simpleClassName}> ${simpleClassName?uncap_first}s = ${simpleClassName?uncap_first}Service.importDataFromJson(json);
        assertNotNull(${simpleClassName?uncap_first}s);
        log.info("{}", ${simpleClassName?uncap_first}s);
    }

}