package com.lzpeng.project.sys.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzpeng.project.sys.domain.Notice;
import com.lzpeng.project.sys.domain.NoticeType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 通知 控制层单元测试
 *
 * @author : 李志鹏
 * @date : 2020-5-16
 * @time : 21:03:56
 */
@Slf4j
@EnableWebMvc
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SuppressWarnings("deprecation")
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
public class NoticeControllerTest {

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
    private static final String MODULE_NAME = "sys";
    /**
     * 类名称
     */
    private static final String CLASS_NAME = "notice";
    /**
     * 通知列表权限
     */
    private static final String LIST_PERM = MODULE_NAME + ":" + CLASS_NAME + ":list";
    /**
     * 通知查询权限
     */
    private static final String QUERY_PERM = MODULE_NAME + ":" + CLASS_NAME + ":query";
    /**
     * 通知新增权限
     */
    private static final String ADD_PERM = MODULE_NAME + ":" + CLASS_NAME + ":add";
    /**
     * 通知删除权限
     */
    private static final String DELETE_PERM = MODULE_NAME + ":" + CLASS_NAME + ":delete";
    /**
     * 通知修改权限
     */
    private static final String EDIT_PERM = MODULE_NAME + ":" + CLASS_NAME + ":edit";
    /**
     * 通知导出权限
     */
    private static final String EXPORT_PERM = MODULE_NAME + ":" + CLASS_NAME + ":export";
    /**
     * 通知导入权限
     */
    private static final String IMPORT_PERM = MODULE_NAME + ":" + CLASS_NAME + ":import";


    @Before
    public void before() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    @Test
    @WithMockUser(username = TEST_USER, authorities = ADD_PERM)
    public void testSave() throws Exception {
        Notice notice = new Notice();
        notice.setNumber("number");
        notice.setName("标题");
        notice.setContent("本人想离职");
        notice.setNoticeType(NoticeType.TOP_SCROLL);
        String content = objectMapper.writeValueAsString(notice);
        String result = mockMvc.perform(post("/sys/notice")
                .content(content) // @RequestBody 解析
                .contentType(MediaType.APPLICATION_JSON_UTF8) // @RequestBody 解
//                .param("xxx", "xxx")// @RequestParam 解析
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // @RequestParam 解析
                .accept(MediaType.APPLICATION_JSON_UTF8)) // 响应类型
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andReturn().getResponse().getContentAsString();
        log.info("{}", result);
    }

    @Test
    @WithMockUser(username = TEST_USER, authorities = DELETE_PERM)
    public void testDelete() throws Exception {
        String id = "{}";
        mockMvc.perform(delete("/sys/notice/" + id)
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 请求类型
                .accept(MediaType.APPLICATION_JSON_UTF8)) // 响应类型
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = TEST_USER, authorities = EDIT_PERM)
    public void testUpdate() throws Exception {
        String id = "{}";
        Notice notice = new Notice();
        String content = objectMapper.writeValueAsString(notice);
        String result = mockMvc.perform(put("/sys/notice/" + id)
                .content(content) // @RequestBody 解析
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)) // 响应类型
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andReturn().getResponse().getContentAsString();
        log.info("{}", result);
    }


    @Test
    @WithMockUser(username = TEST_USER, authorities = QUERY_PERM)
    public void testQuery() throws Exception {
        int page = 1;
        int size = 10;
        Notice notice = new Notice();
        String content = objectMapper.writeValueAsString(notice);
        String result = mockMvc.perform(get("/sys/notice/" + page + "/" + size)
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
        String result = mockMvc.perform(get("/sys/notice/" + id)
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 请求类型
                .accept(MediaType.APPLICATION_JSON_UTF8)) // 响应类型
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andReturn().getResponse().getContentAsString();
        log.info(result);
    }
}