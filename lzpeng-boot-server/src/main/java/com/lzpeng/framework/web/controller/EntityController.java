package com.lzpeng.framework.web.controller;

import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.framework.web.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 获取通用实体信息
 *
 * @date: 2020/5/18
 * @time: 23:05
 * @author: 李志鹏
 */
@RestController
@RequestMapping("/framework/entity")
public class EntityController {

    @Autowired
    private EntityService entityService;

    /**
     * 获取单据信息
     *
     * @param entity
     * @param column
     * @param showColumns
     * @param filter
     * @return
     */
    @GetMapping
    public Result getTableData(String entity, String column, String[] showColumns, String filter, @RequestParam MultiValueMap<String, String> parameters) {
        Map<String, Object> result = entityService.getTableData(entity, column, showColumns, filter, parameters);
        return ResultUtil.success(result);
    }
}
