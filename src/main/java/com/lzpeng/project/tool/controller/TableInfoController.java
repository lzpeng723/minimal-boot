package com.lzpeng.project.tool.controller;

import com.lzpeng.common.response.QueryResult;
import com.lzpeng.common.response.Result;
import com.lzpeng.framework.model.BatchModel;
import com.lzpeng.framework.web.controller.BaseControllerImpl;
import com.lzpeng.project.tool.domain.TableInfo;
import com.lzpeng.project.tool.service.TableInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 表信息 控制层
 * @date: 2020-4-16
 * @time: 11:47:29
 * @author: 李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/tool/tableInfo")
@Api(tags = "表信息管理接口", value = "表信息管理，提供表信息的增、删、改、查")
public class TableInfoController extends BaseControllerImpl<TableInfo> {

    private static final String MODULE_NAME = "tool";
    private static final String CLASS_NAME = "tableInfo";
    private static final String LIST_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":list"; // 表信息列表权限
    private static final String QUERY_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":query"; // 表信息查询权限
    private static final String ADD_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":add"; // 表信息新增权限
    private static final String DELETE_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":delete"; // 表信息删除权限
    private static final String EDIT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":edit"; // 表信息修改权限
    private static final String EXPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":export"; // 表信息导出权限
    private static final String IMPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":import"; // 表信息导入权限

    protected TableInfoService tableInfoService;

    @Autowired
    public void setTableInfoService(TableInfoService tableInfoService) {
        this.baseService = tableInfoService;
        this.baseService = tableInfoService;
        this.tableInfoService = tableInfoService;
    }

    @Override
    @GetMapping("/dict")
    @ApiOperation("获取表信息的数据字典")
    @PreAuthorize("hasAnyAuthority('" + LIST_PERM + "')")
    public Result<TableInfo> getTableInfo() {
        return super.getTableInfo();
    }

    @Override
    @PostMapping
    @ApiOperation("保存表信息")
    @PreAuthorize("hasAnyAuthority('" + ADD_PERM + "')")
    public Result<TableInfo> save(@Valid @RequestBody TableInfo model) {
        return super.save(model);
    }

    @Override
    @DeleteMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("删除表信息")
    @PreAuthorize("hasAnyAuthority('" + DELETE_PERM + "')")
    public Result<Void> delete(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.delete(id);
    }

    @Override
    @PutMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("更新表信息")
    @PreAuthorize("hasAnyAuthority('" + EDIT_PERM + "')")
    public Result<TableInfo> update(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id, @RequestBody TableInfo model) {
        return super.update(id, model);
    }

    @Override
    @GetMapping
    @ApiOperation("查询所有表信息")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<TableInfo>> findAll(TableInfo model) {
        return super.findAll(model);
    }

    @Override
    @GetMapping("/{page:[0-9]+}/{size:[0-9]+}")
    @ApiOperation("分页查询表信息列表")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<QueryResult<TableInfo>> query(@ApiParam(value = "页码", required = true) @PathVariable("page") int page, @ApiParam(value = "每页数据条数", required = true) @PathVariable("size") int size, TableInfo model) {
        return super.query(page, size, model);
    }

    @Override
    @GetMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("根据ID查询表信息")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<TableInfo> findById(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.findById(id);
    }

    @Override
    @PostMapping("/batch")
    @ApiOperation("批量操作表信息")
    @PreAuthorize("hasAuthority('" + ADD_PERM +"') and hasAuthority('" + EDIT_PERM +"') and hasAuthority('" + DELETE_PERM +"')")
    public Result batch(@RequestBody BatchModel<TableInfo> batch) {
        return super.batch(batch);
    }

    @Override
    @PostMapping("/import")
    @ApiOperation("从文件导入数据字典")
    @PreAuthorize("hasAnyAuthority('" + IMPORT_PERM + "')")
    public Result<List<TableInfo>> importData(MultipartFile file) throws IOException {
        return super.importData(file);
    }

    @Override
    @GetMapping("/export")
    @ApiOperation("导出数据字典到文件")
    @PreAuthorize("permitAll()")
    public void exportData(@RequestBody(required = false) List<String> ids, HttpServletResponse response) throws IOException {
        super.exportData(ids, response);
    }

}