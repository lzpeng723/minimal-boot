package com.lzpeng.project.sys.repository;

import com.lzpeng.project.sys.domain.User;
import com.lzpeng.project.sys.repository.UserRepository;
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
 * 用户 数据层单元测试
 * @date: 2020-2-20
 * @time: 23:46:54
 * @author: 李志鹏
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSave(){
        User user = new User();
        user = userRepository.save(user);
        assertNotNull(user.getId());
    }

    @Test
    public void testDelete(){
        String id = "{}";
        userRepository.deleteById(id);
    }

    @Test
    public void testUpdate(){
        String id = "{}";
        Optional<User> optional = userRepository.findById(id);
        optional.ifPresent(user -> {
            user = userRepository.save(user);
            assertEquals(user.getId(), id);
        });
    }

    @Test
    public void testFindAll(){
        Page<User> userPage = userRepository.findAll(PageRequest.of(0, 10));
        assertNotNull(userPage.getContent());
    }

    @Test
    public void testFindById(){
        String id = "{}";
        Optional<User> optional = userRepository.findById(id);
        User user = optional.orElse(null);
        assertNotNull(user);
    }

    @Test
    public void testFindByUsername(){
        String username = "administrator";
        User user = userRepository.findByUsername(username);
        assertNotNull(user);
    }
}