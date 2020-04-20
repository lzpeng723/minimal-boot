package com.lzpeng.project.tool.repository;

import com.lzpeng.project.tool.domain.Gen;
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
 * 代码生成模板 数据层单元测试
 * @date: 2020-4-16
 * @time: 11:47:29
 * @author: 李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class GenRepositoryTest {

    @Autowired
    private GenRepository genRepository;

    @Test
    public void testSave(){
        Gen gen = new Gen();
        gen = genRepository.save(gen);
        assertNotNull(gen.getId());
    }

    @Test
    public void testDelete(){
        String id = "{}";
        genRepository.deleteById(id);
    }

    @Test
    public void testUpdate(){
        String id = "{}";
        Optional<Gen> optional = genRepository.findById(id);
        optional.ifPresent(gen -> {
            gen = genRepository.save(gen);
            assertEquals(gen.getId(), id);
        });
    }

    @Test
    public void testFindAll(){
        Page<Gen> genPage = genRepository.findAll(PageRequest.of(0, 10));
        assertNotNull(genPage.getContent());
    }

    @Test
    public void testFindById(){
        String id = "{}";
        Optional<Gen> optional = genRepository.findById(id);
        Gen gen = optional.orElse(null);
        assertNotNull(gen);
    }
}