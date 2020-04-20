package com.lzpeng.framework.web.repository;

import com.lzpeng.framework.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.util.ClassUtils;

import java.util.List;

/**
 * @date: 2020/2/1
 * @time: 23:36
 * @author: 李志鹏
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {


    /**
     * 得到不在 ids 列表中的实体
     *
     * @param ids id 列表
     * @return
     */
    List<T> findAllByIdNotIn(Iterable<String> ids);

    /**
     * 更新单据状态(由子类实现)
     * @param id 单据id
     * @param enabled 单据状态
     * @return
     */
    default int updateEnabled(@Param("id") String id, @Param("enabled") Boolean enabled) {
        Class<?> userClass = ClassUtils.getUserClass(getClass());
        throw new RuntimeException("请在" + userClass.getName() + "中实现 updateEnabled(String id,Boolean enabled) 方法");
    }

}
