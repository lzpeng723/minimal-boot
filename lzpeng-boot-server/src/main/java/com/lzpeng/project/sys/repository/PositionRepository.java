package com.lzpeng.project.sys.repository;

import com.lzpeng.framework.web.repository.LeftTreeRightTableRepository;
import com.lzpeng.project.sys.domain.Position;
import com.lzpeng.project.sys.domain.Department;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 岗位 数据层
 * @date: 2020-3-20
 * @time: 16:03:56
 * @author: 李志鹏
 */
@Api(tags = "岗位 Entity")
public interface PositionRepository extends LeftTreeRightTableRepository<Department, Position> {

    /**
     * 更新岗位状态
     * @param id 岗位id
     * @param enabled 岗位状态
     * @return
     */
    @Override
    @Modifying
    @Query("UPDATE Position t SET t.enabled = :enabled WHERE t.id = :id")
    int updateEnabled(@Param("id") String id, @Param("enabled") Boolean enabled);


}

