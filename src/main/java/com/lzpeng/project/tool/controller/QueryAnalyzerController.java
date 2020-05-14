package com.lzpeng.project.tool.controller;

import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.project.tool.service.QueryAnalyzerService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 查询分析器Service
     */
    @Autowired
    private QueryAnalyzerService queryAnalyzerService;

    /**
     * 执行 SQL
     * @param map
     * @return
     */
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
    @PostMapping(value = "/jpql", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result executeJPQL(@RequestBody Map<String, String> map){
        Object result = queryAnalyzerService.executeJPQL(map.get("jpql"));
        return ResultUtil.success(result);
    }

}
