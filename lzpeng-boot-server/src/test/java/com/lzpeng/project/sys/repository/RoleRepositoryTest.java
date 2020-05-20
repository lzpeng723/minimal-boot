package com.lzpeng.project.sys.repository;

import com.lzpeng.project.sys.domain.Role;
import com.lzpeng.project.sys.repository.RoleRepository;
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
 * 角色 数据层单元测试
 * @date: 2020-2-20
 * @time: 23:46:54
 * @author: 李志鹏
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testSave(){
        Role role = new Role();
        role = roleRepository.save(role);
        assertNotNull(role.getId());
    }

    @Test
    public void testDelete(){
        String id = "{}";
        roleRepository.deleteById(id);
    }

    @Test
    public void testUpdate(){
        String id = "{}";
        Optional<Role> optional = roleRepository.findById(id);
        optional.ifPresent(role -> {
            role = roleRepository.save(role);
            assertEquals(role.getId(), id);
        });
    }

    @Test
    public void testFindAll(){
        Page<Role> rolePage = roleRepository.findAll(PageRequest.of(0, 10));
        assertNotNull(rolePage.getContent());
    }

    @Test
    public void testFindById(){
        String id = "{}";
        Optional<Role> optional = roleRepository.findById(id);
        Role role = optional.orElse(null);
        assertNotNull(role);
    }
}