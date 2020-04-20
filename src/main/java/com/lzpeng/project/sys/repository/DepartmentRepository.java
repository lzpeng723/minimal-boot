package com.lzpeng.project.sys.repository;

import com.lzpeng.project.sys.domain.Department;
import com.lzpeng.framework.web.repository.TreeRepository;
import io.swagger.annotations.Api;

/**
 * 部门 数据层
 * @date: 2020-3-20
 * @time: 16:03:57
 * @author: 李志鹏
 */
@Api(tags = "部门 Entity")
public interface DepartmentRepository extends TreeRepository<Department> {

}
