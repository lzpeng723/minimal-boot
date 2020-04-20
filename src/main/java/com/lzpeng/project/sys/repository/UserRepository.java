package com.lzpeng.project.sys.repository;

import com.lzpeng.project.sys.domain.User;
import com.lzpeng.framework.web.repository.BaseRepository;
import io.swagger.annotations.Api;

/**
 * 用户 数据层
 * @date: 2020-2-20
 * @time: 23:46:54
 * @author: 李志鹏
 */
@Api(tags = "用户 Entity")
//@RepositoryRestResource
//@Repository
public interface UserRepository extends BaseRepository<User> {


    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username);

}
