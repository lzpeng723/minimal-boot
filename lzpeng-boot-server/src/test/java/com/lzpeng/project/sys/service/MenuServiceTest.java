package com.lzpeng.project.sys.service;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.project.sys.domain.Menu;
import com.lzpeng.project.sys.domain.MenuMeta;
import com.lzpeng.project.sys.domain.MenuType;
import com.lzpeng.project.sys.service.MenuService;
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
 * 菜单 业务层单元测试
 * @date: 2020-2-23
 * @time: 21:00:39
 * @author: 李志鹏
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Test
    public void testSave() {
        Menu menu = new Menu();
        menu.setName("测试");
        menu.setNumber("HOMEasdad");
        menu.setPath("/dashboardadasd");
        menu.setType(MenuType.CONTENT);
        menu.setOrderNum(5);
        MenuMeta meta = new MenuMeta();
        menu.setMeta(meta);
        menu = menuService.save(menu);
        assertNotNull(menu.getId());
    }

    @Test
    public void testDelete() {
        String id = "{}";
        menuService.delete(id);
    }

    @Test
    public void testUpdate() {
        String id = "{}";
        Menu dto = new Menu();
        Menu menu = menuService.update(id, dto);
        assertEquals(menu.getId(), id);
    }
    @Test
    public void testFindById() {
        String id = "{}";
        Menu menu = menuService.findById(id);
        assertEquals(menu.getId(), id);
    }

    @Test
    public void testQuery() {
        Menu dto = new Menu();
        QueryResult<Menu> result = menuService.query(0, 10, dto);
        assertNotNull(result.getList());
    }

    @Test
    public void testReadDataFromJson() throws JsonProcessingException {
        String path = "D:\\Lzpeng723\\Desktop\\rouyi-menu.json";
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<Menu> menus = menuService.readDataFromJson(json);
        log.info("{}", menus);
    }

    @Test
    public void testImportDataFromJson() throws JsonProcessingException {
        String path = "D:\\Lzpeng723\\Desktop\\rouyi-menu.json";
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<Menu> menus = menuService.importDataFromJson(json);
        log.info("{}", menus);
    }

}