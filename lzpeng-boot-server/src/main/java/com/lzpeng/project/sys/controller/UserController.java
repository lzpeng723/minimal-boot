package com.lzpeng.project.sys.controller;

import com.lzpeng.common.response.QueryResult;
import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.framework.model.BatchModel;
import com.lzpeng.framework.web.config.UserAuditor;
import com.lzpeng.framework.web.controller.LeftTreeRightTableControllerImpl;
import com.lzpeng.project.sys.domain.Department;
import com.lzpeng.project.sys.domain.User;
import com.lzpeng.project.sys.service.UserService;
import com.lzpeng.project.tool.domain.TableInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户 控制层
 * @date: 2020-2-23
 * @time: 22:26:10
 * @author: 李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/sys/user")
@Api(tags = "用户管理接口", value = "用户管理，提供用户的增、删、改、查")
public class UserController  extends LeftTreeRightTableControllerImpl<Department, User> {

    private static final String MODULE_NAME = "sys";
    private static final String CLASS_NAME = "user";
    /**
     * 用户列表权限
     */
    private static final String LIST_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":list";
    /**
     * 用户查询权限
     */
    private static final String QUERY_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":query";
    /**
     * 用户新增权限
     */
    private static final String ADD_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":add";
    /**
     * 用户删除权限
     */
    private static final String DELETE_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":delete";
    /**
     * 用户修改权限
     */
    private static final String EDIT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":edit";
    /**
     * 用户导出权限
     */
    private static final String EXPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":export";
    /**
     * 用户导入权限
     */
    private static final String IMPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":import";

    @Autowired
    private UserAuditor userAuditor;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.baseService = userService;
        this.leftTreeRightTableService = userService;
        this.userService = userService;
    }

    @Override
    @GetMapping("/dict")
    @ApiOperation("获取用户的数据字典")
    @PreAuthorize("hasAnyAuthority('" + LIST_PERM + "')")
    public Result<TableInfo> getTableInfo() {
        return super.getTableInfo();
    }

    @Override
    @PostMapping
    @ApiOperation("保存用户")
    @PreAuthorize("hasAnyAuthority('" + ADD_PERM + "')")
    public Result<User> save(@Valid @RequestBody User model) {
        return super.save(model);
    }

    @Override
    @DeleteMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("删除用户")
    @PreAuthorize("hasAnyAuthority('" + DELETE_PERM + "')")
    public Result<Void> delete(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.delete(id);
    }

    @Override
    @PutMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("更新用户")
//    @PreAuthorize("hasAnyAuthority('sys:user:edit') or principal.id.equals(#id)")
    @PreAuthorize("hasAnyAuthority('" + EDIT_PERM + "')")
    public Result<User> update(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id, /*@Valid*/@RequestBody User model) {
        return super.update(id, model);
    }

    @Override
    @GetMapping("/{page:[0-9]+}/{size:[0-9]+}")
    @ApiOperation("分页查询用户列表")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<QueryResult<User>> query(@ApiParam(value = "页码", required = true) @PathVariable("page") int page, @ApiParam(value = "每页数据条数", required = true) @PathVariable("size") int size, User model) {
        return super.query(page, size, model);
    }

    @Override
    @GetMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("根据ID查询用户")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<User> findById(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.findById(id);
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<User> register(@Valid @RequestBody User model){
        User user = userService.register(model);
        return ResultUtil.success(user);
    }

    @GetMapping("/me")
    @ApiOperation("我的信息")
    @PreAuthorize("isAuthenticated()")
    public UserDetails getCurrentUser(){
        return userAuditor.getCurrentUser();
    }

    //public UserDetails getCurrentUser(Authentication authentication, @AuthenticationPrincipal Object principal){}
    @Override
    @PostMapping("/batch")
    @ApiOperation("批量操作数据")
    @PreAuthorize("hasAuthority('" + ADD_PERM +"') and hasAuthority('" + EDIT_PERM +"') and hasAuthority('" + DELETE_PERM +"')")
    public Result batch(@RequestBody BatchModel<User> batch) {
        return super.batch(batch);
    }

    @PostMapping(value = "/setRoles/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("分配角色")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Result<User> setRoles(@ApiParam("用户id") @PathVariable String id, @ApiParam("角色id数组") @RequestBody String[] roles) {
        User user = userService.setRoles(id, roles);
        return ResultUtil.success(user);
    }

    @PostMapping(value = "/hasPermissions")
    @ApiOperation("判断是否有权限")
    @PreAuthorize("isAuthenticated()")
    public Result<String> hasPermissions(@ApiParam("权限编码数组") @RequestBody String[] permissions) {
        Collection<? extends GrantedAuthority> authorities = userAuditor.getCurrentUser().getAuthorities();
        Set<String> authoritySet = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        boolean hasPerm = authoritySet.containsAll(Arrays.asList(permissions));
        return hasPerm ? ResultUtil.success() : ResultUtil.fail("没有对应权限");
    }

    @Override
    @PostMapping("/import")
    @ApiOperation("从文件导入用户")
    @PreAuthorize("hasAnyAuthority('" + IMPORT_PERM + "')")
    public Result<List<User>> importData(MultipartFile file) throws IOException {
        return super.importData(file);
    }

    @Override
    @GetMapping("/export")
    @ApiOperation("导出用户到文件")
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
