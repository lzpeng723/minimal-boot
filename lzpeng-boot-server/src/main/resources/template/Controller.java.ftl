package com.lzpeng.project.${moduleName}.controller;

import com.lzpeng.common.response.QueryResult;
import com.lzpeng.common.response.Result;
import com.lzpeng.framework.model.BatchModel;
import com.lzpeng.framework.web.controller.${entityType}ControllerImpl;
import com.lzpeng.project.tool.domain.TableInfo;
import ${fullClassName};
import com.lzpeng.project.${moduleName}.service.${simpleClassName}Service;<#if entityType=="LeftTreeRightTable">
import ${leftTree.fullClassName};</#if>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
* ${chineseClassName} 控制层
* @date : ${.now?date}
* @time : ${.now?time}
* @author : 李志鹏
*/
@Slf4j
@RestController
@RequestMapping("/${moduleName}/${simpleClassName?uncap_first}")
@Generated(value = "com.lzpeng.project.tool.service.GenService", date = "${.now?date} ${.now?time}", comments = "${chineseClassName} 控制层")
@Api(tags = "${chineseClassName}管理接口", value = "${chineseClassName}管理，提供${chineseClassName}的增、删、改、查")
public class ${simpleClassName}Controller extends ${entityType}ControllerImpl<<#if entityType=="LeftTreeRightTable">${leftTree.simpleClassName}, </#if>${simpleClassName}> {

/**
* 模块名称
*/
private static final String MODULE_NAME = "${moduleName}";
/**
* 类名称
*/
    private static final String CLASS_NAME = "${simpleClassName?uncap_first}";
    /**
    * ${chineseClassName}列表权限
    */
    private static final String LIST_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":list";
    /**
    * ${chineseClassName}查询权限
    */
    private static final String QUERY_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":query";
    /**
    * ${chineseClassName}新增权限
    */
    private static final String ADD_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":add";
    /**
    * ${chineseClassName}删除权限
    */
    private static final String DELETE_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":delete";
    /**
    * ${chineseClassName}修改权限
    */
    private static final String EDIT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":edit";
    /**
    * ${chineseClassName}导出权限
    */
    private static final String EXPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":export";
    /**
    * ${chineseClassName}导入权限
    */
    private static final String IMPORT_PERM = MODULE_NAME + ":" +  CLASS_NAME + ":import";

    /**
    * ${chineseClassName}Service
    */
    private ${simpleClassName}Service ${simpleClassName?uncap_first}Service;

    @Autowired
    public void set${simpleClassName}Service(${simpleClassName}Service ${simpleClassName?uncap_first}Service) {
        this.baseService = ${simpleClassName?uncap_first}Service;<#if entityType??>
        this.${entityType?uncap_first}Service = ${simpleClassName?uncap_first}Service;</#if>
        this.${simpleClassName?uncap_first}Service = ${simpleClassName?uncap_first}Service;
    }

    @Override
    @GetMapping("/dict")
    @ApiOperation("获取${chineseClassName}的数据字典")
    @PreAuthorize("hasAnyAuthority('" + LIST_PERM + "')")
    public Result<TableInfo> getTableInfo() {
        return super.getTableInfo();
    }

    @Override
    @PostMapping
    @ApiOperation("保存${chineseClassName}")
    @PreAuthorize("hasAnyAuthority('" + ADD_PERM + "')")
    public Result<${simpleClassName}> save(@Valid @RequestBody ${simpleClassName} model) {
        return super.save(model);
    }

    @Override
    @DeleteMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("删除${chineseClassName}")
    @PreAuthorize("hasAnyAuthority('" + DELETE_PERM + "')")
    public Result<Void> delete(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.delete(id);
    }

    @Override
    @PutMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("更新${chineseClassName}")
    @PreAuthorize("hasAnyAuthority('" + EDIT_PERM + "')")
    public Result<${simpleClassName}> update(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id, @RequestBody ${simpleClassName} model) {
        return super.update(id, model);
    }

    @Override
    @GetMapping
    @ApiOperation("查询所有${chineseClassName}")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<${simpleClassName}>> findAll(${simpleClassName} model) {
        return super.findAll(model);
    }

    @Override
    @GetMapping("/{page:[0-9]+}/{size:[0-9]+}")
    @ApiOperation("分页查询${chineseClassName}列表")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<QueryResult<${simpleClassName}>> query(@ApiParam(value = "页码", required = true) @PathVariable("page") int page, @ApiParam(value = "每页数据条数", required = true) @PathVariable("size") int size, ${simpleClassName} model) {
        return super.query(page, size, model);
    }

    @Override
    @GetMapping("/{id:^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9]+$}")
    @ApiOperation("根据ID查询${chineseClassName}")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<${simpleClassName}> findById(@ApiParam(value = "主键id", required = true) @PathVariable("id") String id) {
        return super.findById(id);
    }

    @Override
    @PostMapping("/import")
    @ApiOperation("从文件导入${chineseClassName}")
    @PreAuthorize("hasAnyAuthority('" + IMPORT_PERM + "')")
    public Result<List<${simpleClassName}>> importData(MultipartFile file) throws IOException {
        return super.importData(file);
    }

    @Override
    @GetMapping("/export")
    @ApiOperation("导出${chineseClassName}到文件")
    @PreAuthorize("permitAll()")
    public void exportData(@RequestParam(required = false) List<String> ids, HttpServletResponse response) throws IOException {
        super.exportData(ids, response);
    }

    @Override
    @PostMapping("/batch")
    @ApiOperation("批量操作${chineseClassName}")
    @PreAuthorize("hasAuthority('" + ADD_PERM +"') and hasAuthority('" + EDIT_PERM +"') and hasAuthority('" + DELETE_PERM +"')")
    public Result batch(@RequestBody BatchModel<${simpleClassName}> batch) {
        return super.batch(batch);
    }

    <#if entityType=="Tree">
    /**
     * 获取树形结构的${chineseClassName}
     * @param model 查询条件
     * @return
     */
    @Override
    @GetMapping("/tree")
    @ApiOperation("获取树形结构的${chineseClassName}")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<${simpleClassName}>> treeData(${simpleClassName} model) {
        return super.treeData(model);
    }
    </#if>

    <#if entityType=="LeftTreeRightTable">
    /**
    * 获取左树数据
    * @return
    */
    @Override
    @GetMapping("/leftTree")
    @ApiOperation("获取左树数据")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<${leftTree.simpleClassName}>> leftTreeData() {
        return super.leftTreeData();
    }
    </#if>

}