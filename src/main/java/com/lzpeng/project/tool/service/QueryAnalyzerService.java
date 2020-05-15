package com.lzpeng.project.tool.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.TableType;
import com.lzpeng.common.utils.NashornUtil;
import com.lzpeng.common.utils.Radix;
import com.lzpeng.common.utils.RhinoUtil;
import com.lzpeng.framework.domain.BaseEntity;
import com.lzpeng.project.tool.domain.QTableInfo;
import com.lzpeng.project.tool.domain.TableInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mozilla.javascript.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 查询分析器Service
 *
 * @date: 2020/5/14
 * @time: 22:47
 * @author: 李志鹏
 */
@Slf4j
@Service
public class QueryAnalyzerService {

    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 注入的是实体管理器,执行持久化操作
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Jdbc 原生操作
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 数据字典 Service
     */
    @Autowired
    private TableInfoService tableInfoService;

    /**
     * 执行SQL
     * @param sql
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public Object executeSQL(String sql) {
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
        return resultList;
    }

    /**
     * 执行 JPQL
     * @param jpql
     * @return
     */
    @SneakyThrows
    @Transactional(rollbackOn = Exception.class)
    public <T extends BaseEntity> List<T> executeJPQL(String jpql) {
        Query query = entityManager.createQuery(jpql);
        List<T> srcList = query.getResultList();
        List<T> resultList = new ArrayList<>();
        for (T entity : srcList) {
            List<String> ignorePropertyList = new ArrayList<>();
            Class<? extends BaseEntity> entityClass = entity.getClass();
            Field[] fields = ReflectUtil.getFields(entityClass);
            for (Field field : fields) {
                if (Collection.class.isAssignableFrom(field.getType())) {
                    // 忽略集合属性
                    ignorePropertyList.add(field.getName());
                }
            }
            T resultEntity = (T) entityClass.newInstance();
            // 过滤忽略的属性
            String[] ignoreProperties = ignorePropertyList.toArray(new String[ignorePropertyList.size()]);
            BeanUtil.copyProperties(entity, resultEntity, CopyOptions.create()
                    .setIgnoreProperties(ignoreProperties));
            resultList.add(resultEntity);
        }
        return resultList;
    }

    /**
     * 执行 Rhino脚本
     * @param rhino
     * @return
     */
    public Object executeRhino(String rhino) {
        Object result = RhinoUtil.execute(getClass().getName(), rhino, null);
        return Context.toString(result);
    }

    /**
     * 执行 Nashorn脚本
     * @param nashorn
     * @return
     */
    public Object executeNashorn(String nashorn) {
        Object result = NashornUtil.execute(nashorn, null);
        return result;
    }

    /**
     * id 查表名
     * @param id
     * @return
     */
    public TableInfo findTableById(String id) {
        String entityId = Radix.decodeEntityId(id);
        String entityName = StrUtil.subBefore(entityId, ":", false);
        TableInfo tableInfo = tableInfoService.findByClassName(entityName);
        return tableInfo;
    }

    /**
     * 实体查表名
     * @param entityName
     * @return
     */
    public List<TableInfo> findTableByEntity(String entityName) {
        entityName = '%' + entityName + '%';
        List<TableInfo> tableInfos = tableInfoService.findAll(
                QTableInfo.tableInfo.className.likeIgnoreCase(entityName));
        return tableInfos;
    }

    /**
     * 显示所有表
     * @return
     */
    public List<String> findTables() {
        List<String> tableNames = MetaUtil.getTables(dataSource, TableType.TABLE);
        return tableNames;
    }

    /**
     * 查询所有实体
     * @return
     */
    public List<TableInfo> findEntities() {
        List<TableInfo> tableInfoList = tableInfoService.findAll();
        return tableInfoList;
    }
}
