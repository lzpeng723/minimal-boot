package com.lzpeng.project.sys.repository;

import com.lzpeng.framework.web.repository.LeftTreeRightTableRepository;
import com.lzpeng.project.sys.domain.Department;
import com.lzpeng.project.sys.domain.User;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 用户 数据层
 * @date: 2020-2-20
 * @time: 23:46:54
 * @author: 李志鹏
 */
@Api(tags = "用户 Entity")
public interface UserRepository extends LeftTreeRightTableRepository<Department, User> {

    /**
     * 更新用户状态
     * @param id 用户id
     * @param enabled 用户状态
     * @return
     */
    @Override
    @Modifying
    @Query("UPDATE User t SET t.enabled = :enabled WHERE t.id = :id")
    int updateEnabled(@Param("id") String id, @Param("enabled") Boolean enabled);


    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username);

}
