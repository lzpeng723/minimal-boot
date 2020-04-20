package com.lzpeng.project.monitor.controller;

import com.lzpeng.common.response.QueryResult;
import com.lzpeng.common.response.Result;
import com.lzpeng.framework.web.controller.BaseControllerImpl;
import com.lzpeng.project.monitor.domain.MethodLog;
import com.lzpeng.project.monitor.service.MethodLogService;
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
 * 方法日志 控制层
 * @date: 2020-4-6
 * @time: 1:57:47
 * @author: 李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/monitor/methodLog")
@Api(tags = "方法日志管理接口", value = "方法日志管理，提供方法日志的增、删、改、查")
public class MethodLogController extends BaseControllerImpl<MethodLog> {

    private static final String MODULE_NAME = "monitor";
    private static final String CLASS_NAME = "methodLog";
    private static final String LIST_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":list"; // 方法日志列表权限
    private static final String QUERY_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":query"; // 方法日志查询权限
    private static final String ADD_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":add"; // 方法日志新增权限
    private static final String DELETE_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":delete"; // 方法日志删除权限
    private static final String EDIT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":edit"; // 方法日志修改权限
    private static final String EXPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":export"; // 方法日志导出权限
    private static final String IMPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":import"; // 方法日志导入权限

    protected MethodLogService methodLogService;

    @Autowired
    public void setMethodLogService(MethodLogService methodLogService) {
        this.baseService = methodLogService;
        this.baseService = methodLogService;
        this.methodLogService = methodLogService;
    }

    @Override
    @GetMapping("/dict")
    @ApiOperation("获取方法日志的数据字典")
    @PreAuthorize("hasAnyAuthority('" + LIST_PERM + "')")
    public Result<TableInfo> getTableInfo() {
        return super.getTableInfo();
    }

    @Override
    @DeleteMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("删除方法日志")
    @PreAuthorize("hasAnyAuthority('" + DELETE_PERM + "')")
    public Result<Void> delete(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.delete(id);
    }


    @Override
    @GetMapping
    @ApiOperation("查询所有方法日志")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<MethodLog>> findAll(MethodLog model) {
        return super.findAll(model);
    }

    @Override
    @GetMapping("/{page:[0-9]+}/{size:[0-9]+}")
    @ApiOperation("分页查询方法日志列表")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<QueryResult<MethodLog>> query(@ApiParam(value = "页码", required = true) @PathVariable("page") int page, @ApiParam(value = "每页数据条数", required = true) @PathVariable("size") int size, MethodLog model) {
        return super.query(page, size, model);
    }

    @Override
    @GetMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("根据ID查询方法日志")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<MethodLog> findById(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.findById(id);
    }


    @Override
    @GetMapping("/export")
    @ApiOperation("导出方法日志到文件")
    @PreAuthorize("permitAll()")
    public void exportData(@RequestBody(required = false) List<String> ids, HttpServletResponse response) throws IOException {
        super.exportData(ids, response);
    }

}