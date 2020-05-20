package com.lzpeng.framework.web.repository;

import com.lzpeng.framework.domain.TreeEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * @date: 2020/2/1
 * @time: 23:36
 * @author: 李志鹏
 */
@NoRepositoryBean
public interface TreeRepository<T extends TreeEntity<T>> extends BaseRepository<T> {

    /**
     * 返回树形结构
     * @return
     */
    List<T> findByParentNullOrderByOrderNum();
}
