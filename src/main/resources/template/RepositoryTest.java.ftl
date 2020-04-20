package com.lzpeng.project.${moduleName}.repository;

import ${fullClassName};
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
 * ${chineseClassName} 数据层单元测试
 * @date : ${.now?date}
 * @time : ${.now?time}
 * @author : 李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ${simpleClassName}RepositoryTest {

    @Autowired
    private ${simpleClassName}Repository ${simpleClassName?uncap_first}Repository;

    @Test
    public void testSave(){
        ${simpleClassName} ${simpleClassName?uncap_first} = new ${simpleClassName}();
        ${simpleClassName?uncap_first} = ${simpleClassName?uncap_first}Repository.save(${simpleClassName?uncap_first});
        assertNotNull(${simpleClassName?uncap_first}.getId());
    }

    @Test
    public void testDelete(){
        String id = "{}";
        ${simpleClassName?uncap_first}Repository.deleteById(id);
    }

    @Test
    public void testUpdate(){
        String id = "{}";
        Optional<${simpleClassName}> optional = ${simpleClassName?uncap_first}Repository.findById(id);
        optional.ifPresent(${simpleClassName?uncap_first} -> {
            ${simpleClassName?uncap_first} = ${simpleClassName?uncap_first}Repository.save(${simpleClassName?uncap_first});
            assertEquals(${simpleClassName?uncap_first}.getId(), id);
        });
    }

    @Test
    public void testFindAll(){
        Page<${simpleClassName}> ${simpleClassName?uncap_first}Page = ${simpleClassName?uncap_first}Repository.findAll(PageRequest.of(0, 10));
        assertNotNull(${simpleClassName?uncap_first}Page.getContent());
    }

    @Test
    public void testFindById(){
        String id = "{}";
        Optional<${simpleClassName}> optional = ${simpleClassName?uncap_first}Repository.findById(id);
        ${simpleClassName} ${simpleClassName?uncap_first} = optional.orElse(null);
        assertNotNull(${simpleClassName?uncap_first});
    }
}