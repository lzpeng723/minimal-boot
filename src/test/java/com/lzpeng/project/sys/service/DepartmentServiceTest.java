package com.lzpeng.project.sys.service;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.project.sys.domain.Department;
import com.lzpeng.project.sys.service.DepartmentService;
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
 * 部门 业务层单元测试
 * @date: 2020-3-20
 * @time: 16:03:57
 * @author: 李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void testSave() {
        Department department = new Department();
        department = departmentService.save(department);
        assertNotNull(department.getId());
    }

    @Test
    public void testDelete() {
        String id = "{}";
        departmentService.delete(id);
    }

    @Test
    public void testUpdate() {
        String id = "{}";
        Department department = new Department();
        department = departmentService.update(id, department);
        assertEquals(department.getId(), id);
    }
    @Test
    public void testFindById() {
        String id = "{}";
        Department department = departmentService.findById(id);
        assertEquals(department.getId(), id);
    }

    @Test
    public void testQuery() {
        Department department = new Department();
        QueryResult<Department> result = departmentService.query(0, 10, department);
        assertNotNull(result.getList());
    }

    @Test
    public void testReadDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<Department> departments = departmentService.readDataFromJson(json);
        assertNotNull(departments);
        log.info("{}", departments);
    }

    @Test
    public void testImportDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<Department> departments = departmentService.importDataFromJson(json);
        assertNotNull(departments);
        log.info("{}", departments);
    }

}