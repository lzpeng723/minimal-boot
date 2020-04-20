package com.lzpeng.project.monitor.controller;

import com.lzpeng.common.response.QueryResult;
import com.lzpeng.common.response.Result;
import com.lzpeng.framework.web.controller.BaseControllerImpl;
import com.lzpeng.project.monitor.domain.RequestLog;
import com.lzpeng.project.monitor.service.RequestLogService;
import com.lzpeng.project.tool.domain.TableInfo;
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
 * 请求日志 控制层
 * @date: 2020-4-6
 * @time: 1:57:47
 * @author: 李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/monitor/requestLog")
@Api(tags = "请求日志管理接口", value = "请求日志管理，提供请求日志的增、删、改、查")
public class RequestLogController extends BaseControllerImpl<RequestLog> {

    private static final String MODULE_NAME = "monitor";
    private static final String CLASS_NAME = "requestLog";
    private static final String LIST_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":list"; // 请求日志列表权限
    private static final String QUERY_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":query"; // 请求日志查询权限
    private static final String ADD_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":add"; // 请求日志新增权限
    private static final String DELETE_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":delete"; // 请求日志删除权限
    private static final String EDIT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":edit"; // 请求日志修改权限
    private static final String EXPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":export"; // 请求日志导出权限
    private static final String IMPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":import"; // 请求日志导入权限

    protected RequestLogService requestLogService;

    @Autowired
    public void setRequestLogService(RequestLogService requestLogService) {
        this.baseService = requestLogService;
        this.baseService = requestLogService;
        this.requestLogService = requestLogService;
    }

    @Override
    @GetMapping("/dict")
    @ApiOperation("获取请求日志的数据字典")
    @PreAuthorize("hasAnyAuthority('" + LIST_PERM + "')")
    public Result<TableInfo> getTableInfo() {
        return super.getTableInfo();
    }

    @Override
    @DeleteMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("删除请求日志")
    @PreAuthorize("hasAnyAuthority('" + DELETE_PERM + "')")
    public Result<Void> delete(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.delete(id);
    }

    @Override
    @GetMapping
    @ApiOperation("查询所有请求日志")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<RequestLog>> findAll(RequestLog model) {
        return super.findAll(model);
    }

    @Override
    @GetMapping("/{page:[0-9]+}/{size:[0-9]+}")
    @ApiOperation("分页查询请求日志列表")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<QueryResult<RequestLog>> query(@ApiParam(value = "页码", required = true) @PathVariable("page") int page, @ApiParam(value = "每页数据条数", required = true) @PathVariable("size") int size, RequestLog model) {
        return super.query(page, size, model);
    }

    @Override
    @GetMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("根据ID查询请求日志")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<RequestLog> findById(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.findById(id);
    }


    @Override
    @GetMapping("/export")
    @ApiOperation("导出请求日志到文件")
    @PreAuthorize("permitAll()")
    public void exportData(@RequestBody(required = false) List<String> ids, HttpServletResponse response) throws IOException {
        super.exportData(ids, response);
    }

}