package com.lzpeng.project.monitor.controller;

import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.project.monitor.vo.Server;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date: 2020/3/18
 * @time: 23:34
 * @author:   李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/monitor/server")
@Api(tags = "服务监控接口", value = "监控服务器状态")
public class ServerController {

    @GetMapping
    @ApiOperation("获取服务器信息")
    @PreAuthorize("isAuthenticated()")
    public Result<Server> getInfo() {
        Server server = new Server();
        return ResultUtil.success(server);
    }
}
