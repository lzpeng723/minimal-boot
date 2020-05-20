package com.lzpeng.project.sys.repository;

import com.lzpeng.project.sys.domain.Department;
import com.lzpeng.project.sys.repository.DepartmentRepository;
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
 * 部门 数据层单元测试
 * @date: 2020-3-20
 * @time: 16:03:57
 * @author: 李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void testSave(){
        Department department = new Department();
        department = departmentRepository.save(department);
        assertNotNull(department.getId());
    }

    @Test
    public void testDelete(){
        String id = "{}";
        departmentRepository.deleteById(id);
    }

    @Test
    public void testUpdate(){
        String id = "{}";
        Optional<Department> optional = departmentRepository.findById(id);
        optional.ifPresent(department -> {
            department = departmentRepository.save(department);
            assertEquals(department.getId(), id);
        });
    }

    @Test
    public void testFindAll(){
        Page<Department> departmentPage = departmentRepository.findAll(PageRequest.of(0, 10));
        assertNotNull(departmentPage.getContent());
    }

    @Test
    public void testFindById(){
        String id = "{}";
        Optional<Department> optional = departmentRepository.findById(id);
        Department department = optional.orElse(null);
        assertNotNull(department);
    }
}