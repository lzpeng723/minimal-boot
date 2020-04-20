package com.lzpeng.project.tool.repository;

import com.lzpeng.framework.web.repository.BaseRepository;
import com.lzpeng.project.tool.domain.Gen;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 代码生成模板 数据层
 * @date: 2020-4-16
 * @time: 11:47:29
 * @author: 李志鹏
 */
@Api(tags = "代码生成模板 Entity")
public interface GenRepository extends BaseRepository<Gen> {

    /**
    * 更新代码生成模板状态
    * @param id 代码生成模板id
    * @param enabled 代码生成模板状态
    * @return
    */
    @Override
    @Modifying
    @Query("UPDATE Gen t SET t.enabled = :enabled WHERE t.id = :id")
    int updateEnabled(@Param("id") String id, @Param("enabled") Boolean enabled);


}
