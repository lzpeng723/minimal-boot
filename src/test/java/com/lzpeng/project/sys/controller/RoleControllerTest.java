package com.lzpeng.project.sys.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzpeng.project.sys.domain.Role;
import com.lzpeng.framework.web.security.TokenFactory;
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
 * 角色 控制层单元测试
 * @date: 2020-3-6
 * @time: 20:45:53
 * @author: 李志鹏
 */
@Slf4j
@EnableWebMvc
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SuppressWarnings("deprecation")
public class RoleControllerTest {

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
        this.accessToken = this.tokenFactory.getAccessToken("lzpeng", "administrator", "123456");
    }

    
    @Test
    public void testSave() throws Exception {
        Role role = new Role();
        role.setName("asdsa");
        role.setNumber("asdasfasfassa");
        role.setDescription("asasfagegedsa");
        String content = objectMapper.writeValueAsString(role);
        String result = mockMvc.perform(post("/sys/role")
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
        mockMvc.perform(delete("/sys/role/1" + id)
                .header(HttpHeaders.AUTHORIZATION, String.join(" ", accessToken.getTokenType(), accessToken.getValue()))
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 请求类型
                .accept(MediaType.APPLICATION_JSON_UTF8)) // 响应类型
                .andExpect(status().isOk());
    }

    
    @Test
    public void testUpdate() throws Exception {
        String id = "{}";
        Role role = new Role();
        String content = objectMapper.writeValueAsString(role);
        String result = mockMvc.perform(put("/sys/role/" + id)
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
        Role role = new Role();
        String content = objectMapper.writeValueAsString(role);
        String result = mockMvc.perform(get("/sys/role/"+ page + "/" + size)
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
        String result = mockMvc.perform(get("/sys/role/"+ id)
                .header(HttpHeaders.AUTHORIZATION, String.join(" ", accessToken.getTokenType(), accessToken.getValue()))
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 请求类型
                .accept(MediaType.APPLICATION_JSON_UTF8)) // 响应类型
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andReturn().getResponse().getContentAsString();
        log.info(result);
    }
}