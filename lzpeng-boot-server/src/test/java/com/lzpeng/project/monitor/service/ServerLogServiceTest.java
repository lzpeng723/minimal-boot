package com.lzpeng.project.monitor.service;

import com.lzpeng.common.response.QueryResult;
import com.lzpeng.project.monitor.vo.ServerLog;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @date: 2020/4/6
 * @time: 11:51
 * @author:   李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ServerLogServiceTest {

    @Autowired
    private ServerLogService serverLogService;

    @Test
    public void findAll() {
        List<ServerLog> serverLogList = serverLogService.findAll(null);
        log.info("{}", serverLogList);
    }

    @Test
    public void query() {
        QueryResult<ServerLog> query = serverLogService.query(1, 3, null);
        log.info("{}", query);
    }
}