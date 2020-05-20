package com.lzpeng.project.sys.repository;

import com.lzpeng.project.sys.domain.Menu;
import com.lzpeng.project.sys.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 菜单 数据层单元测试
 * @date: 2020-2-20
 * @time: 23:46:54
 * @author: 李志鹏
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    public void testSave(){
        Menu menu = new Menu();
        menu = menuRepository.save(menu);
        assertNotNull(menu.getId());
    }

    @Test
    public void testDelete(){
        String id = "{}";
        menuRepository.deleteById(id);
    }

    @Test
    public void testUpdate(){
        String id = "{}";
        Optional<Menu> optional = menuRepository.findById(id);
        optional.ifPresent(menu -> {
            menu = menuRepository.save(menu);
            assertEquals(menu.getId(), id);
        });
    }

    @Test
    public void testFindAll(){
        Page<Menu> menuPage = menuRepository.findAll(PageRequest.of(0, 100));
        assertNotNull(menuPage.getContent());
    }

    @Test
    public void testFindById(){
        String id = "{}";
        Optional<Menu> optional = menuRepository.findById(id);
        Menu menu = optional.orElse(null);
        assertNotNull(menu);
    }
    @Test
    public void testFindByParentIsNull(){
        List<Menu> menus = menuRepository.findByParentNullOrderByOrderNum();
        assertNotNull(menus);
    }
}