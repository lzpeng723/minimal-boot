package com.lzpeng.project.sys.controller;

import com.lzpeng.common.response.QueryResult;
import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.framework.model.BatchModel;
import com.lzpeng.framework.web.controller.LeftTreeRightTableControllerImpl;
import com.lzpeng.project.sys.domain.Department;
import com.lzpeng.project.sys.domain.Menu;
import com.lzpeng.project.sys.domain.Role;
import com.lzpeng.project.sys.service.RoleService;
import com.lzpeng.project.tool.domain.TableInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 角色 控制层
 * @date: 2020-2-23
 * @time: 22:26:10
 * @author: 李志鹏
 */
@RestController
@RequestMapping("/sys/role")
@Api(tags = "角色管理接口", value = "角色管理，提供角色的增、删、改、查")
public class RoleController extends LeftTreeRightTableControllerImpl<Department, Role> {

    /**
     * 模块名称
     */
    private static final String MODULE_NAME = "sys";
    /**
     * 类名称
     */
    private static final String CLASS_NAME = "role";
    /**
     * 角色列表权限
     */
    private static final String LIST_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":list";
    /**
     * 角色查询权限
     */
    private static final String QUERY_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":query";
    /**
     * 角色新增权限
     */
    private static final String ADD_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":add";
    /**
     * 角色删除权限
     */
    private static final String DELETE_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":delete";
    /**
     * 角色修改权限
     */
    private static final String EDIT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":edit";
    /**
     * 角色导出权限
     */
    private static final String EXPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":export";
    /**
     * 角色导入权限
     */
    private static final String IMPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":import";

    /**
     * 角色Service
     */
    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.baseService = roleService;
        this.leftTreeRightTableService = roleService;
        this.roleService = roleService;
    }

    @Override
    @GetMapping("/dict")
    @ApiOperation("获取角色的数据字典")
    @PreAuthorize("hasAnyAuthority('" + LIST_PERM + "')")
    public Result<TableInfo> getTableInfo() {
        return super.getTableInfo();
    }

    @Override
    @PostMapping
    @ApiOperation("保存角色")
    @PreAuthorize("hasAnyAuthority('" + ADD_PERM + "')")
    public Result<Role> save(@Valid @RequestBody Role model) {
        return super.save(model);
    }

    @Override
    @DeleteMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("删除角色")
    @PreAuthorize("hasAnyAuthority('" + DELETE_PERM + "')")
    public Result<Void> delete(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.delete(id);
    }

    @Override
    @PutMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("更新角色")
    @PreAuthorize("hasAnyAuthority('" + EDIT_PERM + "')")
    public Result<Role> update(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id, /*@Valid*/@RequestBody Role model) {
        return super.update(id, model);
    }

    @Override
    @GetMapping
    @ApiOperation("查询角色列表")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<Role>> findAll(Role model) {
        return super.findAll(model);
    }

    @Override
    @GetMapping("/{page:[0-9]+}/{size:[0-9]+}")
    @ApiOperation("分页查询角色列表")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<QueryResult<Role>> query(@ApiParam(value = "页码", required = true) @PathVariable("page") int page, @ApiParam(value = "每页数据条数", required = true) @PathVariable("size") int size, Role model) {
        return super.query(page, size, model);
    }

    @Override
    @GetMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("根据ID查询角色")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<Role> findById(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.findById(id);
    }

    @Override
    @PostMapping("/batch")
    @ApiOperation("批量操作数据")
    @PreAuthorize("hasAuthority('" + ADD_PERM +"') and hasAuthority('" + EDIT_PERM +"') and hasAuthority('" + DELETE_PERM +"')")
    public Result batch(@RequestBody BatchModel<Role> batch) {
        return super.batch(batch);
    }


    @PostMapping(value = "/setPermissions/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("分配权限")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result<Role> setPermissions(@ApiParam("角色id") @PathVariable String id, @ApiParam("权限id数组") @RequestBody String[] permissions) {
        Role role = roleService.setPermissions(id, permissions);
        return ResultUtil.success(role);
    }

    @GetMapping(value = "/noPermissions/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("得到角色未拥有权限")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result<List<Menu>> noPermissions(@ApiParam("角色id") @PathVariable String id) {
        List<Menu> menus = roleService.noPermissions(id);
        return ResultUtil.success(menus);
    }


    @Override
    @PostMapping("/import")
    @ApiOperation("从文件导入角色")
    @PreAuthorize("hasAnyAuthority('" + IMPORT_PERM + "')")
    public Result<List<Role>> importData(MultipartFile file) throws IOException {
        return super.importData(file);
    }

    @Override
    @GetMapping("/export")
    @ApiOperation("导出角色到文件")
    @PreAuthorize("permitAll()")
    public void exportData(@RequestBody(required = false) List<String> ids, HttpServletResponse response) throws IOException {
        super.exportData(ids, response);
    }
    /**
     * 获取左树数据
     * @return
     */
    @Override
    @GetMapping("/leftTree")
    @ApiOperation("获取左树数据")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<Department>> leftTreeData() {
        return super.leftTreeData();
    }
}
