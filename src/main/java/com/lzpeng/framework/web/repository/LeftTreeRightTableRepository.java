package com.lzpeng.framework.web.repository;

import com.lzpeng.framework.domain.LeftTreeRightTableEntity;
import com.lzpeng.framework.domain.TreeEntity;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @date: 2020/5/3
 * @time: 10:22
 * @author: 李志鹏
 */
@NoRepositoryBean
public interface LeftTreeRightTableRepository<Tree extends TreeEntity<Tree>, Entity extends LeftTreeRightTableEntity<Tree>> extends BaseRepository<Entity> {
}
