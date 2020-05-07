package com.lzpeng.framework.web.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.framework.domain.LeftTreeRightTableEntity;
import com.lzpeng.framework.domain.TreeEntity;
import com.lzpeng.framework.util.TreeEntityUtil;
import com.lzpeng.framework.web.repository.LeftTreeRightTableRepository;
import lombok.SneakyThrows;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 左树右表 Service
 * @date: 2020/5/2
 * @time: 21:40
 * @author: 李志鹏
 */
@Transactional(rollbackOn = Exception.class)
public abstract class LeftTreeRightTableServiceImpl<Tree extends TreeEntity<Tree>, Entity extends LeftTreeRightTableEntity<Tree>> extends BaseServiceImpl<Entity>{


    /**
     * 左树 Service
     */
    protected TreeServiceImpl<Tree> treeService;

    /**
     * 左树右表 Repository
     */
    protected LeftTreeRightTableRepository<Tree, Entity> leftTreeRightTableRepository;

    /**
     * 保存前操作
     * @param entity 右表数据
     * @return
     */
    @Override
    protected boolean beforeSave(Entity entity) {
        if (entity.getTreeId() != null) {
            entity.setTree(treeService.findById(entity.getTreeId()));
        }
        return true;
    }

    /**
     * 获得左树数据
     * @return
     */
    public List<Tree> leftTreeData(){
        return treeService.treeData();
    }

    @Override
    @SneakyThrows
    public QueryResult<Entity> query(int page, int size, Entity model) {
        if (model != null && StrUtil.isNotEmpty(model.getTreeId())){
            Tree tree = getLeftTreeClass().newInstance();
            tree.setId(model.getTreeId());
            model.setTree(tree);
        }
        return super.query(page, size, model);
    }

    /**
     * 获得右表数据
     * @param treeId 树节点id
     * @param model 查询条件
     * @return
     */

    public List<Entity> rightTableData(String treeId, Entity model){
        List<Entity> entityList = super.findAll(model);
        Tree root = treeService.findById(treeId);
        List<Tree> flatData = TreeEntityUtil.flatData(root);
        List<String> treeIds = flatData.stream().map(TreeEntity::getId).collect(Collectors.toList());
        return entityList.stream().filter(entity -> treeIds.contains(entity.getTree().getId())).collect(Collectors.toList());
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
