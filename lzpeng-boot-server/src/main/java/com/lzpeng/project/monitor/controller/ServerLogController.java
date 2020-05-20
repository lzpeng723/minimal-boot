package com.lzpeng.project.monitor.controller;

import com.lzpeng.common.response.QueryResult;
import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.project.monitor.vo.ServerLog;
import com.lzpeng.project.monitor.service.ServerLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 后台日志 控制层
 * @date: 2020-4-6
 * @time: 1:57:47
 * @author: 李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/monitor/serverLog")
@Api(tags = "后台日志管理接口", value = "后台日志管理，提供后台日志的增、删、改、查")
public class ServerLogController {

    private static final String MODULE_NAME = "monitor";
    private static final String CLASS_NAME = "serverLog";
    private static final String LIST_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":list"; // 后台日志列表权限
    private static final String QUERY_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":query"; // 后台日志查询权限
    private static final String ADD_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":add"; // 后台日志新增权限
    private static final String DELETE_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":delete"; // 后台日志删除权限
    private static final String EDIT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":edit"; // 后台日志修改权限
    private static final String EXPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":export"; // 后台日志导出权限
    private static final String IMPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":import"; // 后台日志导入权限

    @Autowired
    protected ServerLogService serverLogService;


    @GetMapping
    @ApiOperation("查询所有后台日志")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<ServerLog>> findAll(ServerLog model) {
        List<ServerLog> serverLogList = serverLogService.findAll(model);
        return ResultUtil.success(serverLogList);
    }

    @GetMapping("/{page:[0-9]+}/{size:[0-9]+}")
    @ApiOperation("分页查询后台日志列表")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<QueryResult<ServerLog>> query(@ApiParam(value = "页码", required = true) @PathVariable("page") int page, @ApiParam(value = "每页数据条数", required = true) @PathVariable("size") int size, ServerLog model) {
        QueryResult<ServerLog> serverLogList = serverLogService.query(page, size, model);
        return ResultUtil.success(serverLogList);
    }

    @GetMapping("/download/{fileName}")
    @ApiOperation("导出后台日志")
    @PreAuthorize("permitAll()")
    public void download(@ApiParam("日志文件名") @PathVariable String fileName, HttpServletResponse response) throws IOException {
        serverLogService.downloadLogFile(fileName, response);
    }





}