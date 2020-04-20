package com.lzpeng.project.tool.service;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import com.lzpeng.common.utils.EnvUtil;
import com.lzpeng.framework.annotation.BooleanValue;
import com.lzpeng.framework.annotation.DefaultBooleanValue;
import com.lzpeng.framework.annotation.IntValue;
import com.lzpeng.framework.domain.IntEnum;
import com.lzpeng.project.tool.domain.ColumnInfo;
import com.lzpeng.project.tool.domain.DictValue;
import com.lzpeng.project.tool.domain.TableInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.persister.walking.spi.AttributeDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @date: 2020/3/18
 * @time: 19:07
 * @author:   李志鹏
 * 初始化数据字典到数据库
 */
@Slf4j
@Service
public class TableInfoInitService {


    private final SessionFactoryImpl sessionFactory;

    private final DataSource dataSource;

    private final TableInfoService tableInfoService;

    @Autowired
    public TableInfoInitService(EntityManagerFactory entityManagerFactory, DataSource dataSource, TableInfoService tableInfoService) {
        this.dataSource = dataSource;
        this.tableInfoService = tableInfoService;
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
    }

    /**
     * 将数据字典信息保存至数据库
     * @throws Exception
     */
    public void initTableInfo() throws Exception {
        // 如果是开发环境 删除所有数据字典
        if (EnvUtil.isDev()) {
            tableInfoService.deleteAll();
        }
        List<TableInfo> tableInfos = getTableInfo();
        tableInfoService.saveAll(tableInfos);
        log.info("初始化数据字典成功");
    }

    /**
     * 得到数据字典数据
     * @throws Exception
     * @return
     */
    public List<TableInfo> getTableInfo() throws Exception {
        List<TableInfo> tableInfos = new ArrayList<>();
        List<String> tableNames = tableInfoService.findTableNames(); // 查询已生成字典的表名
        // 获取所有实体信息
        Map<String, EntityPersister> entityPersisterMap = sessionFactory.getMetamodel().entityPersisters();
        for (EntityPersister entityPersister : entityPersisterMap.values()) {
            if (entityPersister instanceof SingleTableEntityPersister) {
                SingleTableEntityPersister persister = (SingleTableEntityPersister) entityPersister;
                if (!tableNames.contains(persister.getTableName())) {
                    // 当数据库中没有该表的数据字典信息时才自动生成
                    TableInfo tableInfo = getTableInfo(persister);
                    tableInfos.add(tableInfo);
                }
            }
        }
        return tableInfos;
    }

    /**
     * 通过 persister 获取数据字典
     * @param persister SingleTableEntityPersister Hibernate对象 可以获取此实体对应表信息
     * @return
     */
    private TableInfo getTableInfo(SingleTableEntityPersister persister) throws Exception {
        String tableName = persister.getTableName(); // 表名
        String entityName = persister.getEntityName(); // 实体名
        Table tableMeta = MetaUtil.getTableMeta(dataSource, tableName); // 表元数据
        TableInfo tableInfo = new TableInfo(); // 数据字典
        tableInfo.setClassName(entityName); // 实体类名
        tableInfo.setComment(tableMeta.getComment());// 表注释
        tableInfo.setTableName(tableName); // 表名
        Class<?> entityClass = Class.forName(entityName); // 实体类
        Object apiModel = AnnotationUtil.getAnnotationValue(entityClass, ApiModel.class); // 实体类上的ApiModel注解
        tableInfo.setApiModel(Optional.ofNullable(apiModel).map(String::valueOf).orElse(null));
        List<ColumnInfo> columnInfos = getColumnInfos(persister, tableMeta, entityClass);
        tableInfo.getColumns().addAll(columnInfos);
        return tableInfo;
    }

    /**
     * 通过 persister 和  tableMeta 获取表的列详细信息
     * @param persister SingleTableEntityPersister Hibernate对象 可以获取此实体对应表信息
     * @param tableMeta
     * @return
     */
    private List<ColumnInfo> getColumnInfos(SingleTableEntityPersister persister, Table tableMeta, Class<?> entityClass) {
        List<ColumnInfo> columnInfos = new ArrayList<>();
        for (AttributeDefinition attribute : persister.getAttributes()) {
            ColumnInfo columnInfo = new ColumnInfo();
            String propertyName = attribute.getName(); //在entity中的属性名称
            String columnName = persister.getPropertyColumnNames(propertyName)[0]; //对应数据库表中的字段名
            Column column = tableMeta.getColumn(columnName); // 列元数据
            columnInfo.setColumnName(columnName); //列名
            columnInfo.setFieldName(propertyName);// 字段名
            columnInfo.setComment(column.getComment()); // 列注释
            columnInfo.setNullable(column.isNullable()); // 是否可以为 null
            columnInfo.setSize(column.getSize());
            columnInfo.setType(column.getType());
            columnInfo.setTypeName(column.getTypeName());
            Field field = ReflectUtil.getField(entityClass, propertyName);
            columnInfo.setFieldName(field.getName());
            columnInfo.setClassName(field.getType().getName());
            Object apiModelProperty = AnnotationUtil.getAnnotationValue(field, ApiModelProperty.class);
            columnInfo.setApiModelProperty(Optional.ofNullable(apiModelProperty).map(String::valueOf).orElse(null));
            List<DictValue> dictValues = getDictValues(field);
            columnInfo.getDictValues().addAll(dictValues);
            columnInfos.add(columnInfo);
        }
        return columnInfos;
    }

    /**
     * 获取该字段可以存哪些值
     * @param field
     * @return
     */
    private List<DictValue> getDictValues(Field field) {
        List<DictValue> dictValues = new ArrayList<>();
        Class<?> type = field.getType();
        // 获得 int 型字段的数据字典
        if (int.class.isAssignableFrom(type) || Integer.class.isAssignableFrom(type)) {
            IntValue[] intValues = field.getAnnotationsByType(IntValue.class);
            if (intValues != null && intValues.length > 0){
                for (int i = 0; i < intValues.length; i++) {
                    IntValue intValue = intValues[i];
                    DictValue dictValue = new DictValue(); // 字典信息
                    dictValue.setKey(intValue.key()); // 数据库中存的值
                    dictValue.setValue(intValue.value()); // 前端展示的值
                    dictValue.setOrderNum(i + 1); // 顺序号
                    dictValues.add(dictValue);
                }
            }
        }

        // 获得 枚举 型字段的数据字典
        if (IntEnum.class.isAssignableFrom(type) && type.isEnum()) {
            IntEnum[] enums = (IntEnum[]) type.getEnumConstants();
            for (IntEnum anEnum : enums) {
                DictValue dictValue = new DictValue(); // 字典信息
                dictValue.setKey(anEnum.getCode()); // 数据库中存的值
                dictValue.setValue(anEnum.getMessage()); // 前端展示的值
                dictValue.setOrderNum(anEnum.getCode() + 1); // 顺序号
                dictValues.add(dictValue);
            }
        }
        // 获得 boolean 型字段的数据字典
        if (boolean.class.isAssignableFrom(type) || Boolean.class.isAssignableFrom(type)) {
            BooleanValue booleanValue = field.getAnnotation(BooleanValue.class);
            if (booleanValue == null) {
                booleanValue = new DefaultBooleanValue();
            }
            String trueValue = booleanValue.trueValue();
            String falseValue = booleanValue.falseValue();
            Boolean defaultValue = booleanValue.defaultValue();
            DictValue trueDictValue = new DictValue();
            trueDictValue.setKey(1);
            trueDictValue.setValue(trueValue);
            trueDictValue.setDefaulted(defaultValue);
            DictValue falseDictValue = new DictValue();
            falseDictValue.setKey(0);
            falseDictValue.setValue(falseValue);
            falseDictValue.setDefaulted(!defaultValue);
            dictValues.add(trueDictValue);
            dictValues.add(falseDictValue);
        }
        return dictValues;
    }

}
