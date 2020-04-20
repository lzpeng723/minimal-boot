package com.lzpeng.project.tool.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzpeng.framework.web.security.TokenFactory;
import com.lzpeng.project.tool.domain.Gen;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 代码生成模板 控制层单元测试
 * @date: 2020-4-16
 * @time: 11:47:29
 * @author: 李志鹏
 */
@Slf4j
@EnableWebMvc
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SuppressWarnings("deprecation")
public class GenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenFactory tokenFactory;

    private ObjectMapper objectMapper;

    private OAuth2AccessToken accessToken;

    @Before
    public void before(){
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        this.accessToken = this.tokenFactory.getAccessToken("lzpeng", "user", "123456");
    }

    
    @Test
    public void testSave() throws Exception {
        Gen gen = new Gen();
        String content = objectMapper.writeValueAsString(gen);
        String result = mockMvc.perform(post("/tool/gen")
                .header(HttpHeaders.AUTHORIZATION, String.join(" ", accessToken.getTokenType(), accessToken.getValue()))
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
    public void testDelete() throws Exception {
        String id = "{}";
        mockMvc.perform(delete("/tool/gen/1" + id)
                .header(HttpHeaders.AUTHORIZATION, String.join(" ", accessToken.getTokenType(), accessToken.getValue()))
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 请求类型
                .accept(MediaType.APPLICATION_JSON_UTF8)) // 响应类型
                .andExpect(status().isOk());
    }

    
    @Test
    public void testUpdate() throws Exception {
        String id = "{}";
        Gen gen = new Gen();
        String content = objectMapper.writeValueAsString(gen);
        String result = mockMvc.perform(put("/tool/gen/" + id)
                .header(HttpHeaders.AUTHORIZATION, String.join(" ", accessToken.getTokenType(), accessToken.getValue()))
                .content(content) // @RequestBody 解析
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)) // 响应类型
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andReturn().getResponse().getContentAsString();
        log.info("{}",result);
    }

    
    @Test
    public void testQuery() throws Exception {
        int page = 1;
        int size = 10;
        Gen gen = new Gen();
        String content = objectMapper.writeValueAsString(gen);
        String result = mockMvc.perform(get("/tool/gen/"+ page + "/" + size)
                .header(HttpHeaders.AUTHORIZATION, String.join(" ", accessToken.getTokenType(), accessToken.getValue()))
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 请求类型
                .accept(MediaType.APPLICATION_JSON_UTF8)) // 响应类型
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.total").isNumber())
                .andReturn().getResponse().getContentAsString();
        log.info(result);
    }

    
    @Test
    public void testFindById() throws Exception {
        String id = "{}";
        String result = mockMvc.perform(get("/tool/gen/"+ id)
                .header(HttpHeaders.AUTHORIZATION, String.join(" ", accessToken.getTokenType(), accessToken.getValue()))
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 请求类型
                .accept(MediaType.APPLICATION_JSON_UTF8)) // 响应类型
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andReturn().getResponse().getContentAsString();
        log.info(result);
    }
}