package com.lzpeng.project.tool.repository;

import com.lzpeng.framework.web.repository.BaseRepository;
import com.lzpeng.project.tool.domain.TableInfo;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 表信息 数据层
 * @date: 2020-4-16
 * @time: 11:47:29
 * @author: 李志鹏
 */
@Api(tags = "表信息 Entity")
public interface TableInfoRepository extends BaseRepository<TableInfo> {

    /**
    * 更新表信息状态
    * @param id 表信息id
    * @param enabled 表信息状态
    * @return
    */
    @Override
    @Modifying
    @Query("UPDATE TableInfo t SET t.enabled = :enabled WHERE t.id = :id")
    int updateEnabled(@Param("id") String id, @Param("enabled") Boolean enabled);

    /**
     * 查找所有表名
     * @return
     */
    @Query("SELECT t.tableName FROM TableInfo t")
    List<String> findTableNames();

    /**
     * 根据实体类名查找表信息
     * @param className
     * @return
     */
    TableInfo findByClassName(String className);


}
