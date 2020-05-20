package com.lzpeng.project.tool.controller;

import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.framework.domain.BaseEntity;
import com.lzpeng.project.tool.domain.TableInfo;
import com.lzpeng.project.tool.service.QueryAnalyzerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 查询分析器Controller
 * @date: 2020/5/14
 * @time: 22:45
 * @author: 李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/tool/queryAnalyzer")
@Api(tags = "查询分析器接口", value = "查询分析器，提供执行SQL,JPQL,Rhino,Nashorn,ID查表名等功能")
public class QueryAnalyzerController {

    /**
     * 系统工具模块权限名称
     */
    private static final String MODULE_NAME = "tool";
    /**
     * 查询分析器模块权限名称
     */
    private static final String CLASS_NAME = "queryAnalyzer";
    /**
     * 查询分析器列表权限
     */
    private static final String LIST_PERM = MODULE_NAME + ":" + CLASS_NAME + ":list";

    /**
     * 查询分析器查询权限
     */
    private static final String QUERY_PERM = MODULE_NAME + ":" + CLASS_NAME + ":query";

    /**
     * 查询分析器执行脚本权限
     */
    private static final String EXECUTE_PERM = MODULE_NAME + ":" + CLASS_NAME + ":execute";

    /**
     * 查询分析器Service
     */
    @Autowired
    private QueryAnalyzerService queryAnalyzerService;

    /**
     * 执行 SQL
     * @param map
     * @return
     */
    @ApiOperation("执行SQL")
    @PostMapping(value = "/sql", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result executeSQL(@RequestBody Map<String, String> map){
        Object result = queryAnalyzerService.executeSQL(map.get("sql"));
        return ResultUtil.success(result);
    }

    /**
     * 执行 JPQL
     * @param map
     * @return
     */
    @ApiOperation("执行JPQL")
    @PostMapping(value = "/jpql", produces = MediaType.APPLICATION_JSON_VALUE)
    public <T extends BaseEntity> Result<List<T>> executeJPQL(@RequestBody Map<String, String> map){
        List<T> result = queryAnalyzerService.executeJPQL(map.get("jpql"));
        return ResultUtil.success(result);
    }

    /**
     * 执行Rhino脚本
     * @param map
     * @return
     */
    @ApiOperation("执行Rhino脚本")
    @PreAuthorize("hasAnyAuthority('" + EXECUTE_PERM + "')")
    @PostMapping(value = "/rhino", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<Object> executeRhino(@RequestBody Map<String, String> map){
        Object result = queryAnalyzerService.executeRhino(map.get("rhino"));
        return ResultUtil.success(result);
    }

    /**
     * 执行Nashorn脚本
     * @param map
     * @return
     */
    @ApiOperation("执行Nashorn脚本")
    @PreAuthorize("hasAnyAuthority('" + EXECUTE_PERM + "')")
    @PostMapping(value = "/nashorn", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<Object> executeNashorn(@RequestBody Map<String, String> map){
        Object result = queryAnalyzerService.executeNashorn(map.get("nashorn"));
        return ResultUtil.success(result);
    }

    /**
     * id 查表名
     * @param id
     * @return
     */
    @GetMapping("/id")
    @ApiOperation("id查表名")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<TableInfo> findTableById(@ApiParam("单据id") String id){
        TableInfo result = queryAnalyzerService.findTableById(id);
        return ResultUtil.success(result);
    }

    /**
     * 实体查表名
     * @param entityName
     * @return
     */
    @GetMapping("/entity")
    @ApiOperation("实体查表名")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<TableInfo>> findTableByEntity(@ApiParam("实体名称") String entityName){
        List<TableInfo> result = queryAnalyzerService.findTableByEntity(entityName);
        return ResultUtil.success(result);
    }

    /**
     * 显示所有表
     * @return
     */
    @GetMapping("/tables")
    @ApiOperation("显示所有表")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<String>> findTables(){
        List<String> result = queryAnalyzerService.findTables();
        return ResultUtil.success(result);
    }

    /**
     * 显示所有实体
     * @return
     */
    @GetMapping("/entities")
    @ApiOperation("显示所有实体")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<List<TableInfo>> findEntities(){
        List<TableInfo> result = queryAnalyzerService.findEntities();
        return ResultUtil.success(result);
    }

}
