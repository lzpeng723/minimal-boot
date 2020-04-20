package com.lzpeng.project.sys.controller;

import com.lzpeng.common.response.QueryResult;
import com.lzpeng.common.response.Result;
import com.lzpeng.framework.model.BatchModel;
import com.lzpeng.framework.web.controller.TreeControllerImpl;
import com.lzpeng.project.sys.domain.Department;
import com.lzpeng.project.sys.service.DepartmentService;
import com.lzpeng.project.tool.domain.TableInfo;
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
 * 部门 控制层
 * @date: 2020-3-20
 * @time: 16:03:57
 * @author: 李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/sys/department")
@Api(tags = "部门管理接口", value = "部门管理，提供部门的增、删、改、查")
public class DepartmentController extends TreeControllerImpl<Department> {

    private static final String MODULE_NAME = "sys";
    private static final String CLASS_NAME = "department";
    /**
     * 部门列表权限
     */
    private static final String LIST_PERM = MODULE_NAME + ":" + CLASS_NAME + ":list";
    /**
     * 部门查询权限
     */
    private static final String QUERY_PERM = MODULE_NAME + ":" + CLASS_NAME + ":query";
    /**
     * 部门新增权限
     */
    private static final String ADD_PERM = MODULE_NAME + ":" + CLASS_NAME + ":add";
    /**
     * 部门删除权限
     */
    private static final String DELETE_PERM = MODULE_NAME + ":" + CLASS_NAME + ":delete";
    /**
     * 部门修改权限
     */
    private static final String EDIT_PERM = MODULE_NAME + ":" + CLASS_NAME + ":edit";
    /**
     * 部门导出权限
     */
    private static final String EXPORT_PERM = MODULE_NAME + ":" + CLASS_NAME + ":export";
    /**
     * 部门导入权限
     */
    private static final String IMPORT_PERM = MODULE_NAME + ":" + CLASS_NAME + ":import";
    
    protected DepartmentService departmentService;

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.baseService = departmentService;
        this.treeService = departmentService;
        this.departmentService = departmentService;
    }

    @Override
    @GetMapping("/dict")
    @ApiOperation("获取部门的数据字典")
    @PreAuthorize("hasAnyAuthority('" + LIST_PERM + "')")
    public Result<TableInfo> getTableInfo() {
        return super.getTableInfo();
    }

    @Override
    @PostMapping
    @ApiOperation("保存部门")
    @PreAuthorize("hasAnyAuthority('" + ADD_PERM + "')")
    public Result<Department> save(@Valid @RequestBody Department model) {
        return super.save(model);
    }

    @Override
    @DeleteMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("删除部门")
    @PreAuthorize("hasAnyAuthority('" + DELETE_PERM + "')")
    public Result<Void> delete(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.delete(id);
    }

    @Override
    @PutMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("更新部门")
    @PreAuthorize("hasAnyAuthority('" + EDIT_PERM + "')")
    public Result<Department> update(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id, @RequestBody Department model) {
        return super.update(id, model);
    }

    @Override
    @GetMapping
    @ApiOperation("查询所有部门")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<Department>> findAll(Department model) {
        return super.findAll(model);
    }

    @Override
    @GetMapping("/{page:[0-9]+}/{size:[0-9]+}")
    @ApiOperation("分页查询部门列表")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<QueryResult<Department>> query(@ApiParam(value = "页码", required = true) @PathVariable("page") int page, @ApiParam(value = "每页数据条数", required = true) @PathVariable("size") int size, Department model) {
        return super.query(page, size, model);
    }

    @Override
    @GetMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("根据ID查询部门")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<Department> findById(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.findById(id);
    }

    /**
     * 获取树形结构的部门
     * @return
     */
    @Override
    @GetMapping("/tree")
    @ApiOperation("获取树形结构的部门")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<Department>> treeData(Department model) {
        return super.treeData(model);
    }

    @Override
    @PostMapping("/batch")
    @ApiOperation("批量操作数据")
    @PreAuthorize("hasAuthority('" + ADD_PERM +"') and hasAuthority('" + EDIT_PERM +"') and hasAuthority('" + DELETE_PERM +"')")
    public Result batch(@RequestBody BatchModel<Department> batch) {
        return super.batch(batch);
    }

    @Override
    @PostMapping("/import")
    @ApiOperation("从文件导入部门")
    @PreAuthorize("hasAnyAuthority('" + IMPORT_PERM + "')")
    public Result<List<Department>> importData(MultipartFile file) throws IOException {
        return super.importData(file);
    }

    @Override
    @GetMapping("/export")
    @ApiOperation("导出部门到文件")
    @PreAuthorize("permitAll()")
    public void exportData(@RequestBody(required = false) List<String> ids, HttpServletResponse response) throws IOException {
        super.exportData(ids, response);
    }
}
