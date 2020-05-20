package com.lzpeng.project.sys.repository;

import com.lzpeng.project.sys.domain.Position;
import com.lzpeng.project.sys.repository.PositionRepository;
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
 * 岗位 数据层单元测试
 * @date: 2020-3-20
 * @time: 16:03:56
 * @author: 李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PositionRepositoryTest {

    @Autowired
    private PositionRepository positionRepository;

    @Test
    public void testSave(){
        Position position = new Position();
        position = positionRepository.save(position);
        assertNotNull(position.getId());
    }

    @Test
    public void testDelete(){
        String id = "{}";
        positionRepository.deleteById(id);
    }

    @Test
    public void testUpdate(){
        String id = "{}";
        Optional<Position> optional = positionRepository.findById(id);
        optional.ifPresent(position -> {
            position = positionRepository.save(position);
            assertEquals(position.getId(), id);
        });
    }

    @Test
    public void testFindAll(){
        Page<Position> positionPage = positionRepository.findAll(PageRequest.of(0, 10));
        assertNotNull(positionPage.getContent());
    }

    @Test
    public void testFindById(){
        String id = "{}";
        Optional<Position> optional = positionRepository.findById(id);
        Position position = optional.orElse(null);
        assertNotNull(position);
    }
}