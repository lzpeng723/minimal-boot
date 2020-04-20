package com.lzpeng.project.${moduleName}.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import ${fullClassName};
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ${chineseClassName} 控制层单元测试
 * @date : ${.now?date}
 * @time : ${.now?time}
 * @author : 李志鹏
 */
@Slf4j
@EnableWebMvc
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SuppressWarnings("deprecation")
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
public class ${simpleClassName}ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    /**
    * 测试用户
    */
    private static final String TEST_USER = "test-user";
    /**
    * 模块名称
    */
    private static final String MODULE_NAME = "${moduleName}";
    /**
    * 类名称
    */
    private static final String CLASS_NAME = "${simpleClassName?uncap_first}";
    /**
    * ${chineseClassName}列表权限
    */
    private static final String LIST_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":list";
    /**
    * ${chineseClassName}查询权限
    */
    private static final String QUERY_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":query";
    /**
    * ${chineseClassName}新增权限
    */
    private static final String ADD_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":add";
    /**
    * ${chineseClassName}删除权限
    */
    private static final String DELETE_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":delete";
    /**
    * ${chineseClassName}修改权限
    */
    private static final String EDIT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":edit";
    /**
    * ${chineseClassName}导出权限
    */
    private static final String EXPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":export";
    /**
    * ${chineseClassName}导入权限
    */
    private static final String IMPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":import";


    @Before
    public void before(){
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);}

    @Test
    @WithMockUser(username = TEST_USER, authorities = ADD_PERM)
    public void testSave() throws Exception {
        ${simpleClassName} ${simpleClassName?uncap_first} = new ${simpleClassName}();<#if entityType=="Tree">
        ${simpleClassName?uncap_first}.setOrderNum(1);</#if>
        String content = objectMapper.writeValueAsString(${simpleClassName?uncap_first});
        String result = mockMvc.perform(post("/${moduleName}/${simpleClassName?uncap_first}")
                .content(content) // @RequestBody 解析
                .contentType(MediaType.APPLICATION_JSON_UTF8) // @RequestBody 解
//                .param("xxx", "xxx")// @RequestParam 解析
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // @RequestParam 解析
                .accept(MediaType.APPLICATION_JSON_UTF8)) // 响应类型
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andReturn().getResponse().getContentAsString();
        log.info("{}",result);
    }

    @Test
    @WithMockUser(username = TEST_USER, authorities = DELETE_PERM)
    public void testDelete() throws Exception {
        String id = "{}";
        mockMvc.perform(delete("/${moduleName}/${simpleClassName?uncap_first}/" + id)
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 请求类型
                .accept(MediaType.APPLICATION_JSON_UTF8)) // 响应类型
                .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = TEST_USER, authorities = EDIT_PERM)
    public void testUpdate() throws Exception {
        String id = "{}";
        ${simpleClassName} ${simpleClassName?uncap_first} = new ${simpleClassName}();
        String content = objectMapper.writeValueAsString(${simpleClassName?uncap_first});
        String result = mockMvc.perform(put("/${moduleName}/${simpleClassName?uncap_first}/" + id)
                .content(content) // @RequestBody 解析
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)) // 响应类型
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andReturn().getResponse().getContentAsString();
        log.info("{}",result);
    }

    
    @Test
    @WithMockUser(username = TEST_USER, authorities = QUERY_PERM)
    public void testQuery() throws Exception {
        int page = 1;
        int size = 10;
        ${simpleClassName} ${simpleClassName?uncap_first} = new ${simpleClassName}();
        String content = objectMapper.writeValueAsString(${simpleClassName?uncap_first});
        String result = mockMvc.perform(get("/${moduleName}/${simpleClassName?uncap_first}/"+ page + "/" + size)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 请求类型
                .accept(MediaType.APPLICATION_JSON_UTF8)) // 响应类型
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.total").isNumber())
                .andReturn().getResponse().getContentAsString();
        log.info(result);
    }

    
    @Test
    @WithMockUser(username = TEST_USER, authorities = QUERY_PERM)
    public void testFindById() throws Exception {
        String id = "{}";
        String result = mockMvc.perform(get("/${moduleName}/${simpleClassName?uncap_first}/"+ id)
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 请求类型
                .accept(MediaType.APPLICATION_JSON_UTF8)) // 响应类型
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andReturn().getResponse().getContentAsString();
        log.info(result);
    }
}