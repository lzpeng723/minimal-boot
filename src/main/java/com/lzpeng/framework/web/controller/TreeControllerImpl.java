package com.lzpeng.framework.web.controller;

import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.framework.web.service.TreeServiceImpl;
import com.lzpeng.framework.domain.TreeEntity;

import java.util.List;

/**
 * 基础的Controller
 * @date: 2020/2/5
 * @time: 22:12
 * @author: 李志鹏
 */
public class TreeControllerImpl<Entity extends TreeEntity<Entity>> extends BaseControllerImpl<Entity> {

    /**
     * 泛型注入
     */
    protected TreeServiceImpl<Entity> treeService;

    /**
     * 得到树形数据
     * @return
     */
    public Result<List<Entity>> treeData(){
        List<Entity> entities = treeService.treeData();
        return ResultUtil.success(entities);
    }

    /**
     * 根据条件查询树形数据
     * @param model
     * @return
     */
    public Result<List<Entity>> treeData(Entity model) {
        List<Entity> entities = treeService.treeData(model);
        return ResultUtil.success(entities);
    }


}
