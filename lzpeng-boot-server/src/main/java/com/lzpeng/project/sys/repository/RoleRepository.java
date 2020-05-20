package com.lzpeng.project.sys.repository;

import com.lzpeng.framework.web.repository.LeftTreeRightTableRepository;
import com.lzpeng.project.sys.domain.Department;
import com.lzpeng.project.sys.domain.Role;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 角色 数据层
 * @date: 2020-2-20
 * @time: 23:46:54
 * @author: 李志鹏
 */
@Api(tags = "角色 Entity")
public interface RoleRepository extends LeftTreeRightTableRepository<Department, Role> {

    /**
     * 更新角色状态
     * @param id 角色id
     * @param enabled 角色状态
     * @return
     */
    @Override
    @Modifying
    @Query("UPDATE Role t SET t.enabled = :enabled WHERE t.id = :id")
    int updateEnabled(@Param("id") String id, @Param("enabled") Boolean enabled);

}
