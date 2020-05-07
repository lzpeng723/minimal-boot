package com.lzpeng.framework.web.controller;

import cn.hutool.core.util.TypeUtil;
import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.framework.domain.LeftTreeRightTableEntity;
import com.lzpeng.framework.domain.TreeEntity;
import com.lzpeng.framework.web.service.LeftTreeRightTableServiceImpl;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 左树右表Controller
 * @date: 2020/5/3
 * @time: 00:00
 * @author: 李志鹏
 */
public class LeftTreeRightTableControllerImpl<Tree extends TreeEntity<Tree>, Entity extends LeftTreeRightTableEntity<Tree>> extends BaseControllerImpl<Entity> {

    /**
     * 泛型注入
     */
    protected LeftTreeRightTableServiceImpl<Tree, Entity> leftTreeRightTableService;

    /**
     * 获得左树数据
     * @return
     */
    public Result<List<Tree>> leftTreeData(){
        List<Tree> leftTreeData = leftTreeRightTableService.leftTreeData();
        return ResultUtil.success(leftTreeData);
    }

    /**
     * 获得右表数据
     * @param treeId 树节点id
     * @param model 查询条件
     * @return
     */
    public Result<List<Entity>> rightTableData(String treeId, Entity model){
        List<Entity> rightTableData = leftTreeRightTableService.rightTableData(treeId, model);
        return ResultUtil.success(rightTableData);
    }

    /**
     * 得到左树类型
     * @return
     */
    protected Class<Tree> getLeftTreeClass(){
        Type type = TypeUtil.getTypeArgument(getClass());
        if (type != null && type instanceof Class) {
            return (Class<Tree>) type;
        }
        return null;
    }

    /**
     * 得到右表类型
     * @return
     */
    protected Class<Entity> getRightTableClass(){
        Type type = TypeUtil.getTypeArgument(getClass(), 1);
        if (type != null && type instanceof Class) {
            return (Class<Entity>) type;
        }
        return null;
    }

    @Override
    protected Class<Entity> getEntityClass() {
        return this.getRightTableClass();
    }
}
