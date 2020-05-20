package com.lzpeng.project.sys.controller;

import com.lzpeng.common.response.QueryResult;
import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.framework.model.BatchModel;
import com.lzpeng.framework.web.controller.TreeControllerImpl;
import com.lzpeng.project.sys.domain.Menu;
import com.lzpeng.project.sys.service.MenuService;
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
 * 菜单 控制层
 * @date: 2020-2-23
 * @time: 22:26:10
 * @author: 李志鹏
 */
@RestController
@RequestMapping("/sys/menu")
@Api(tags = "菜单管理接口", value = "菜单管理，提供菜单的增、删、改、查")
public class MenuController extends TreeControllerImpl<Menu>  {

    /**
     * 模块名称
     */
    private static final String MODULE_NAME = "sys";
    /**
     * 类名称
     */
    private static final String CLASS_NAME = "menu";
    /**
     *  菜单列表权限
     */
    private static final String LIST_PERM = MODULE_NAME + ":" + CLASS_NAME + ":list";
    /**
     *  菜单查询权限
     */
    private static final String QUERY_PERM = MODULE_NAME + ":" + CLASS_NAME + ":query";
    /**
     * 菜单新增权限
     */
    private static final String ADD_PERM = MODULE_NAME + ":" + CLASS_NAME + ":add";
    /**
     * 菜单删除权限
     */
    private static final String DELETE_PERM = MODULE_NAME + ":" + CLASS_NAME + ":delete";
    /**
     * 菜单修改权限
     */
    private static final String EDIT_PERM = MODULE_NAME + ":" + CLASS_NAME + ":edit";
    /**
     * 菜单导出权限
     */
    private static final String EXPORT_PERM = MODULE_NAME + ":" + CLASS_NAME + ":export";
    /**
     * 菜单导入权限
     */
    private static final String IMPORT_PERM = MODULE_NAME + ":" + CLASS_NAME + ":import";

    private MenuService menuService;

    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
        this.treeService = menuService;
        this.baseService = menuService;
    }
    @Override
    @GetMapping("/dict")
    @ApiOperation("获取菜单的数据字典")
    @PreAuthorize("hasAnyAuthority('" + LIST_PERM + "')")
    public Result<TableInfo> getTableInfo() {
        return super.getTableInfo();
    }

    @Override
    @PostMapping
    @ApiOperation("保存菜单")
    @PreAuthorize("hasAnyAuthority('" + ADD_PERM + "')")
    public Result<Menu> save(@Valid @RequestBody Menu entity) {
        return super.save(entity);
    }

    @Override
    @DeleteMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("删除菜单")
    @PreAuthorize("hasAnyAuthority('" + DELETE_PERM + "')")
    public Result<Void> delete(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.delete(id);
    }

    @Override
    @PutMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("更新菜单")
    @PreAuthorize("hasAnyAuthority('" + EDIT_PERM + "')")
    public Result<Menu> update(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id, /*@Valid*/@RequestBody Menu model) {
        return super.update(id, model);
    }

    @Override
    @GetMapping("/{page:[0-9]+}/{size:[0-9]+}")
    @ApiOperation("分页查询菜单列表")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<QueryResult<Menu>> query(@ApiParam(value = "页码", required = true) @PathVariable("page") int page, @ApiParam(value = "每页数据条数", required = true) @PathVariable("size") int size, /*@RequestBody(required = false) */Menu model) {
        return super.query(page, size, model);
    }

    @Override
    @GetMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("根据ID查询菜单")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<Menu> findById(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.findById(id);
    }

    /**
     * 获取树形结构的菜单
     * @return
     */
    @Override
    @GetMapping("/tree")
    @ApiOperation("获取树形结构的菜单")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<Menu>> treeData(Menu menu) {
        return super.treeData(menu);
    }

    @Override
    @GetMapping
    @ApiOperation("查询所有菜单")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<Menu>> findAll(Menu model) {
        Result<List<Menu>> result = super.findAll(model);
        return result;
    }

    @Override
    @PostMapping("/batch")
    @ApiOperation("批量操作数据")
    @PreAuthorize("hasAuthority('" + ADD_PERM +"') and hasAuthority('" + EDIT_PERM +"') and hasAuthority('" + DELETE_PERM +"')")
    public Result batch(@RequestBody BatchModel<Menu> batch) {
        return super.batch(batch);
    }

    @GetMapping("/routers")
    @ApiOperation("获取用户路由菜单")
    @PreAuthorize("isAuthenticated()")
    public Result<List<Menu>> getRouters() {
        List<Menu> routers = menuService.getRouters();
        return ResultUtil.success(routers);
    }

    @GetMapping("/allRouters")
    @ApiOperation("获取所有路由菜单")
    @PreAuthorize("isAuthenticated()")
    public Result<List<Menu>> getAllRouters() {
        List<Menu> routers = menuService.getAllRouters();
        return ResultUtil.success(routers);
    }
    @Override
    @PostMapping("/import")
    @ApiOperation("从文件导入菜单")
    @PreAuthorize("hasAnyAuthority('" + IMPORT_PERM + "')")
    public Result<List<Menu>> importData(MultipartFile file) throws IOException {
        return super.importData(file);
    }

    @Override
    @GetMapping("/export")
    @ApiOperation("导出菜单到文件")
    @PreAuthorize("permitAll()")
    public void exportData(@RequestBody(required = false) List<String> ids, HttpServletResponse response) throws IOException {
        super.exportData(ids, response);
    }
}
