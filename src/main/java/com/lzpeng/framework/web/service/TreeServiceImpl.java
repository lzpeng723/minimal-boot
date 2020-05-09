package com.lzpeng.framework.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.framework.domain.TreeEntity;
import com.lzpeng.framework.util.TreeEntityUtil;
import com.lzpeng.framework.web.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 基础的 树形结构service
 * @date: 2020/2/4
 * @time: 15:14
 * @author: 李志鹏
 */
public abstract class TreeServiceImpl<Entity extends TreeEntity<Entity>> extends BaseServiceImpl<Entity> {


    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * 泛型注入
     */
    protected TreeRepository<Entity> treeRepository;

    /**
     * 保存 遍历子节点 保存所有
     * 如果有父节点 将此对象加入父节点的孩子节点，并保存父节点
     * @param entity
     * @return
     */
    @Override
    public Entity save(Entity entity) {
        // 如果传了父节点id，则从数据库查询节点，级联保存
        if (entity.getParentId() != null) {
            Entity parent = findById(entity.getParentId());
            entity.setParent(parent);
        }
        // 队列，先进先出保存子节点
        Collection<Entity> entities = new HashSet<>();
        Queue<Entity> queue = new ConcurrentLinkedQueue<>();
        // 入队
        queue.offer(entity);
        while (!queue.isEmpty()) {
            // 出队
            Entity parent = queue.remove();
            // 给 每个 子节点 设置 parent
            for (Entity child : parent.getChildren()) {
                child.setParent(parent);
                // 入队
                queue.offer(child);
            }
            parent.getChildren().clear();
            entities.add(parent);
        }
        // 不能调 super.saveAll 会无限递归
        if (beforeSaveAll(entities)) {
            List<Entity> result = treeRepository.saveAll(entities);
            return result.get(0);
        }
        return null;
    }


    /**
     * 查询所有数据
     *
     * @param model
     * @return
     */
    @Override
    public List<Entity> findAll(Entity model) {
        List<Entity> entities = super.findAll(model);
        // 将其父节点以及祖先节点加入返回结果中
        List<Entity> result = TreeEntityUtil.flatData(entities);
        // 查询后操作
        // afterFindAll(result);
        return result;
    }


    /**
     * 分页查询数据
     *
     * @param page
     * @param size
     * @param model
     * @return
     */
    @Override
    public QueryResult<Entity> query(int page, int size, Entity model) {
        QueryResult<Entity> query = super.query(page, size, model);
        List<Entity> entities = query.getList();
        List<Entity> result = TreeEntityUtil.flatData(entities);
        // 查询后操作
        // afterFindAll(result);
        return new QueryResult<>(result, query.getTotal(), query.getPage(), query.getTotalPage());
    }

    /**
     * 查询树形结构数据
     *
     * @param model
     * @return
     */
    public List<Entity> treeData(Entity model) {
        if (model == null) {
            return treeData();
        }
        List<Entity> entities = super.findAll(model);
        List<Entity> result = TreeEntityUtil.treeData(entities);
        // afterFindAll(result);
        return result;
    }

    /**
     * 查询所有树形结构数据
     *
     * @return
     */
    public List<Entity> treeData() {
        List<Entity> entities = treeRepository.findByParentNullOrderByOrderNum();
        // 排序 设置父id
        TreeEntityUtil.sortTreeData(entities);
        // 查询后操作
        // afterFindAll(entities);
        return entities;
    }




}
