package com.lzpeng.project.sys.controller;

import com.lzpeng.common.response.QueryResult;
import com.lzpeng.common.response.Result;
import com.lzpeng.framework.model.BatchModel;
import com.lzpeng.framework.web.controller.BaseControllerImpl;
import com.lzpeng.project.sys.domain.Position;
import com.lzpeng.project.sys.service.PositionService;
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
 * 岗位 控制层
 * @date: 2020-3-20
 * @time: 16:03:56
 * @author: 李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/sys/position")
@Api(tags = "岗位管理接口", value = "岗位管理，提供岗位的增、删、改、查")
public class PositionController extends BaseControllerImpl<Position> {

    private static final String MODULE_NAME = "sys";
    private static final String CLASS_NAME = "position";
    private static final String LIST_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":list"; // 岗位列表权限
    private static final String QUERY_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":query"; // 岗位查询权限
    private static final String ADD_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":add"; // 岗位新增权限
    private static final String DELETE_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":delete"; // 岗位删除权限
    private static final String EDIT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":edit"; // 岗位修改权限
    private static final String EXPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":export"; // 岗位导出权限
    private static final String IMPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":import"; // 岗位导入权限
    protected PositionService positionService;

    @Autowired
    public void setPositionService(PositionService positionService) {
        this.baseService = positionService;
        this.positionService = positionService;
    }

    @Override
    @GetMapping("/dict")
    @ApiOperation("获取岗位的数据字典")
    @PreAuthorize("hasAnyAuthority('" + LIST_PERM + "')")
    public Result<TableInfo> getTableInfo() {
        return super.getTableInfo();
    }

    @Override
    @PostMapping
    @ApiOperation("保存岗位")
    @PreAuthorize("hasAnyAuthority('" + ADD_PERM + "')")
    public Result<Position> save(@Valid @RequestBody Position model) {
        return super.save(model);
    }

    @Override
    @DeleteMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("删除岗位")
    @PreAuthorize("hasAnyAuthority('" + DELETE_PERM + "')")
    public Result<Void> delete(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.delete(id);
    }

    @Override
    @PutMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("更新岗位")
    @PreAuthorize("hasAnyAuthority('" + EDIT_PERM + "')")
    public Result<Position> update(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id, @RequestBody Position model) {
        return super.update(id, model);
    }

    @Override
    @GetMapping
    @ApiOperation("查询所有岗位")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<Position>> findAll(Position model) {
        return super.findAll(model);
    }

    @Override
    @GetMapping("/{page:[0-9]+}/{size:[0-9]+}")
    @ApiOperation("分页查询岗位列表")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<QueryResult<Position>> query(@ApiParam(value = "页码", required = true) @PathVariable("page") int page, @ApiParam(value = "每页数据条数", required = true) @PathVariable("size") int size, Position model) {
        return super.query(page, size, model);
    }

    @Override
    @GetMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("根据ID查询岗位")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<Position> findById(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.findById(id);
    }


    @Override
    @PostMapping("/batch")
    @ApiOperation("批量操作数据")
    @PreAuthorize("hasAuthority('" + ADD_PERM +"') and hasAuthority('" + EDIT_PERM +"') and hasAuthority('" + DELETE_PERM +"')")
    public Result batch(@RequestBody BatchModel<Position> batch) {
        return super.batch(batch);
    }

    @Override
    @PostMapping("/import")
    @ApiOperation("从文件导入岗位")
    @PreAuthorize("hasAnyAuthority('" + IMPORT_PERM + "')")
    public Result<List<Position>> importData(MultipartFile file) throws IOException {
        return super.importData(file);
    }

    @Override
    @GetMapping("/export")
    @ApiOperation("导出岗位到文件")
    @PreAuthorize("permitAll()")
    public void exportData(@RequestBody(required = false) List<String> ids, HttpServletResponse response) throws IOException {
        super.exportData(ids, response);
    }
}
