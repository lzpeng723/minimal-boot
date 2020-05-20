package com.lzpeng.project.sys.service;

import cn.hutool.core.io.FileUtil;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.project.sys.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 用户 业务层单元测试
 * @date: 2020-2-23
 * @time: 21:00:39
 * @author: 李志鹏
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    //private UserDetailsPasswordService

    @Autowired
    private UserDetailsService userDetailsService;


    @Test
    public void testSave() {
        User user = new User();
        user = userService.save(user);
        assertNotNull(user.getId());
    }

    @Test
    public void testDelete() {
        String id = "{}";
        userService.delete(id);
    }

    @Test
    public void testUpdate() {
        String id = "{}";
        User dto = new User();
        User user = userService.update(id, dto);
        assertEquals(user.getId(), id);
    }
    @Test
    public void testFindById() {
        String id = "{}";
        User user = userService.findById(id);
        assertEquals(user.getId(), id);
    }

    @Test
    public void testQuery() {
        User dto = new User();
        QueryResult<User> result = userService.query(0, 10, dto);
        assertNotNull(result.getList());
    }
    @Test
    public void testFindByUsername(){
        String username = "administrator";
        User user = userService.findByUsername(username);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        assertNotNull(user);
    }

    @Test
    public void testExportDataToExcel() throws IOException {
        InputStream inputStream = userService.exportDataToExcel(null);
        File file = new File("User.xlsx").getAbsoluteFile();
        Files.createDirectories(file.getParentFile().toPath());
        FileUtil.writeFromStream(inputStream, file);
        log.info("{}", file.getAbsolutePath());
    }

    @Test
    public void testReadDataFromExcel() throws IOException {
        File file = new File("User.xlsx").getAbsoluteFile();
        InputStream inputStream = Files.newInputStream(file.toPath());
        List<User> users = userService.readDataFromExcel(inputStream);
        log.info("{}", users.size());
    }


}