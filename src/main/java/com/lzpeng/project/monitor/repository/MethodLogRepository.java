package com.lzpeng.project.monitor.repository;

import com.lzpeng.framework.web.repository.BaseRepository;
import com.lzpeng.project.monitor.domain.MethodLog;
import io.swagger.annotations.Api;

/**
 * 方法日志 数据层
 * @date: 2020-4-6
 * @time: 1:57:47
 * @author: 李志鹏
 */
@Api(tags = "方法日志 Entity")
public interface MethodLogRepository extends BaseRepository<MethodLog> {

}
