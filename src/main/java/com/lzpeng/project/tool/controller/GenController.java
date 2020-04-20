package com.lzpeng.project.tool.controller;

import com.lzpeng.common.response.QueryResult;
import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.framework.model.BatchModel;
import com.lzpeng.framework.web.controller.BaseControllerImpl;
import com.lzpeng.project.tool.domain.Gen;
import com.lzpeng.project.tool.domain.TableInfo;
import com.lzpeng.project.tool.service.GenService;
import com.lzpeng.project.tool.vo.GenInfo;
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
import java.util.Map;

/**
 * 代码生成模板 控制层
 * @date: 2020-4-16
 * @time: 11:47:29
 * @author: 李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/tool/gen")
@Api(tags = "代码生成模板管理接口", value = "代码生成模板管理，提供代码生成模板的增、删、改、查")
public class GenController extends BaseControllerImpl<Gen> {

    private static final String MODULE_NAME = "tool";
    private static final String CLASS_NAME = "gen";
    private static final String LIST_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":list"; // 代码生成模板列表权限
    private static final String QUERY_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":query"; // 代码生成模板查询权限
    private static final String ADD_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":add"; // 代码生成模板新增权限
    private static final String DELETE_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":delete"; // 代码生成模板删除权限
    private static final String EDIT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":edit"; // 代码生成模板修改权限
    private static final String EXPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":export"; // 代码生成模板导出权限
    private static final String IMPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":import"; // 代码生成模板导入权限    private static final String genPerm = MODULE_NAME + ":" +  CLASS_NAME + ":gen"; // 代码生成权限
    private static final String GEN_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":gen"; // 代码生成权限



    protected GenService genService;

    @Autowired
    public void setGenService(GenService genService) {
        this.baseService = genService;
        this.baseService = genService;
        this.genService = genService;
    }

    @Override
    @GetMapping("/dict")
    @ApiOperation("获取代码生成模板的数据字典")
    @PreAuthorize("hasAnyAuthority('" + LIST_PERM + "')")
    public Result<TableInfo> getTableInfo() {
        return super.getTableInfo();
    }

    @Override
    @PostMapping
    @ApiOperation("保存代码生成模板")
    @PreAuthorize("hasAnyAuthority('" + ADD_PERM + "')")
    public Result<Gen> save(@Valid @RequestBody Gen model) {
        return super.save(model);
    }

    @Override
    @DeleteMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("删除代码生成模板")
    @PreAuthorize("hasAnyAuthority('" + DELETE_PERM + "')")
    public Result<Void> delete(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.delete(id);
    }

    @Override
    @PutMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("更新代码生成模板")
    @PreAuthorize("hasAnyAuthority('" + EDIT_PERM + "')")
    public Result<Gen> update(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id, @RequestBody Gen model) {
        return super.update(id, model);
    }

    @Override
    @GetMapping
    @ApiOperation("查询所有代码生成模板")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<Gen>> findAll(Gen model) {
        return super.findAll(model);
    }

    @Override
    @GetMapping("/{page:[0-9]+}/{size:[0-9]+}")
    @ApiOperation("分页查询代码生成模板列表")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<QueryResult<Gen>> query(@ApiParam(value = "页码", required = true) @PathVariable("page") int page, @ApiParam(value = "每页数据条数", required = true) @PathVariable("size") int size, Gen model) {
        return super.query(page, size, model);
    }

    @Override
    @GetMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("根据ID查询代码生成模板")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<Gen> findById(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.findById(id);
    }

    @Override
    @PostMapping("/batch")
    @ApiOperation("批量操作代码生成模板")
    @PreAuthorize("hasAuthority('" + ADD_PERM +"') and hasAuthority('" + EDIT_PERM +"') and hasAuthority('" + DELETE_PERM +"')")
    public Result batch(@RequestBody BatchModel<Gen> batch) {
        return super.batch(batch);
    }

    @GetMapping("/gen/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("根据实体类生成文件")
    @PreAuthorize("permitAll()")
    public <T> void gen(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id, @RequestParam("CLASS_NAME") Class<T> clazz, HttpServletResponse response) throws IOException {
        genService.downloadCode(clazz, id, response);
    }

    @GetMapping("/gen")
    @ApiOperation("根据实体类生成所有文件")
    @PreAuthorize("permitAll()")
    public <T> void gen(@RequestParam("CLASS_NAME") Class<T> clazz, HttpServletResponse response) throws IOException {
        genService.downloadCode(clazz, response);
    }

    @GetMapping("/preview/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("预览实体类生成的文件")
    @PreAuthorize("hasAnyAuthority('" + GEN_PERM +"')")
    public <T> Result<Map<String, String>> preview(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id, @RequestParam("CLASS_NAME") Class<T> clazz) {
        Map<String, String> map = genService.preview(clazz, id);
        return ResultUtil.success(map);
    }

    @GetMapping("/genInfo")
    @ApiOperation("查询实体类代码生成信息")
    @PreAuthorize("hasAnyAuthority('" + GEN_PERM +"')")
    public <T> Result<List<GenInfo>> genning(@RequestParam("CLASS_NAME") Class<T> clazz) {
        List<GenInfo> genInfoList = genService.genningCode(clazz);
        return ResultUtil.success(genInfoList);
    }

    @Override
    @PostMapping("/import")
    @ApiOperation("从文件导入代码模板")
    @PreAuthorize("hasAnyAuthority('" + IMPORT_PERM + "')")
    public Result<List<Gen>> importData(MultipartFile file) throws IOException {
        return super.importData(file);
    }

    @Override
    @GetMapping("/export")
    @ApiOperation("导出代码模板到文件")
    @PreAuthorize("permitAll()")
    public void exportData(@RequestBody(required = false) List<String> ids, HttpServletResponse response) throws IOException {
        super.exportData(ids, response);
    }

}