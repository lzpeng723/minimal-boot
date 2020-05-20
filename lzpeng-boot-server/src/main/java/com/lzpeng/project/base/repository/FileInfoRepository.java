package com.lzpeng.project.base.repository;

import com.lzpeng.project.base.domain.FileInfo;
import com.lzpeng.framework.web.repository.BaseRepository;
import io.swagger.annotations.Api;

/**
 * @date: 2020/3/10
 * @time: 23:17
 * @author:   李志鹏
 */
@Api("文件信息 Api")
public interface FileInfoRepository extends BaseRepository<FileInfo> {
}
