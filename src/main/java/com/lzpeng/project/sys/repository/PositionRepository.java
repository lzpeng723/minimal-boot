package com.lzpeng.project.sys.repository;

import com.lzpeng.project.sys.domain.Position;
import com.lzpeng.framework.web.repository.BaseRepository;
import io.swagger.annotations.Api;

/**
 * 岗位 数据层
 * @date: 2020-3-20
 * @time: 16:03:56
 * @author: 李志鹏
 */
@Api(tags = "岗位 Entity")
public interface PositionRepository extends BaseRepository<Position> {

}
