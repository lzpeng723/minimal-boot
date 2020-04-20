package com.lzpeng.project.sys.service;

import com.lzpeng.common.response.QueryResult;
import com.lzpeng.project.sys.domain.Role;
import com.lzpeng.project.sys.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 角色 业务层单元测试
 * @date: 2020-2-23
 * @time: 21:00:39
 * @author: 李志鹏
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void testSave() {
        Role role = new Role();
        role = roleService.save(role);
        assertNotNull(role.getId());
    }

    @Test
    public void testDelete() {
        String id = "{}";
        roleService.delete(id);
    }

    @Test
    public void testUpdate() {
        String id = "{}";
        Role dto = new Role();
        Role role = roleService.update(id, dto);
        assertEquals(role.getId(), id);
    }
    @Test
    public void testFindById() {
        String id = "{}";
        Role role = roleService.findById(id);
        assertEquals(role.getId(), id);
    }

    @Test
    public void testQuery() {
        Role dto = new Role();
        QueryResult<Role> result = roleService.query(0, 10, dto);
        assertNotNull(result.getList());
    }

}