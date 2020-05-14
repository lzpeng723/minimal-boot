package com.lzpeng.framework.web.controller;

import cn.hutool.core.util.TypeUtil;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.framework.domain.BaseEntity;
import com.lzpeng.framework.model.BatchModel;
import com.lzpeng.framework.web.service.BaseServiceImpl;
import com.lzpeng.project.tool.domain.TableInfo;
import com.querydsl.core.types.Predicate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 基础的Controller
 * @date: 2020/2/5
 * @time: 22:12
 * @author: 李志鹏
 * 谁说 HTTP GET 就不能通过 Body 来发送数据呢？ https://www.jianshu.com/p/c025273d78db
 */
public class BaseControllerImpl<Entity extends BaseEntity>  {


    /**
     * 泛型注入
     */
    protected BaseServiceImpl<Entity> baseService;


    /**
     * 获取数据字典
     * @throws IOException
     * @return
     */
    public Result<TableInfo> getTableInfo() {
        TableInfo tableInfo = baseService.getTableInfo();
        return ResultUtil.success(tableInfo);
    }

    /**
     * 保存
     * @param entity
     * @return
     */
    
    public Result<Entity> save(Entity entity) {
        entity = baseService.save(entity);
        return ResultUtil.success(entity);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    
    public Result<Void> delete(String id) {
        baseService.delete(id);
        return ResultUtil.success();
    }

    /**
     * 更新
     * @param id
     * @param model
     * @return
     */
    
    public Result<Entity> update(String id, Entity model) {
        Entity entity = baseService.update(id, model);
        return ResultUtil.success(entity);
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @param model
     * @return
     */
    public Result<QueryResult<Entity>> query(int page, int size, Entity model) {
        QueryResult<Entity> result = baseService.query(page, size, model);
        return ResultUtil.success(result);
    }

    /**
     * QueryDsl分页查询
     * @param page
     * @param size
     * @param predicate
     * @return
     */
    public Result<QueryResult<Entity>> query(int page, int size, Predicate predicate) {
        QueryResult<Entity> result = baseService.query(page, size, predicate);
        return ResultUtil.success(result);
    }

    /**
     * 根据id查询单条数据
     * @param id
     * @return
     */
    
    public Result<Entity> findById(String id) {
        Entity entity = baseService.findById(id);
        return ResultUtil.success(entity);
    }

    /**
     * 查询所有数据
     * @param model
     * @return
     */
    
    public Result<List<Entity>> findAll(Entity model) {
        List<Entity> entities = baseService.findAll(model);
        return ResultUtil.success(entities);
    }

    /**
     * 得到泛型参数
     * @return
     */
    protected Class<Entity> getEntityClass(){
        Type type = TypeUtil.getTypeArgument(getClass());
        if (type != null && type instanceof Class) {
            return (Class<Entity>) type;
        }
        return null;
    }


    /**
     * 批量增删改查
     * @param batch 批量操作的数据
     * @return
     */
    public Result batch(BatchModel<Entity> batch){
        Object result = baseService.batch(batch);
        return ResultUtil.success(result);
    }

    /**
     * 导入数据
     * @param file 上传的文件
     * @return
     */
    public Result<List<Entity>> importData(MultipartFile file) throws IOException {
        List<Entity> list = baseService.importData(file);
        return ResultUtil.success(list);
    }

    /**
     * 导出数据到文件
     * @return
     */
    public void exportData(List<String> ids, HttpServletResponse response) throws IOException {
        baseService.exportData(ids, response);
    }
}
