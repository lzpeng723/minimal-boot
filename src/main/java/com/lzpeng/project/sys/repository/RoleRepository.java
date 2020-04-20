package com.lzpeng.project.sys.repository;

import com.lzpeng.project.sys.domain.Role;
import com.lzpeng.framework.web.repository.BaseRepository;
import io.swagger.annotations.Api;

/**
 * 角色 数据层
 * @date: 2020-2-20
 * @time: 23:46:54
 * @author: 李志鹏
 */
@Api(tags = "角色 Entity")
//@RepositoryRestResource
public interface RoleRepository extends BaseRepository<Role> {

}
