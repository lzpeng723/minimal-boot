package com.lzpeng.project.sys.repository;

import com.lzpeng.framework.web.repository.BaseRepository;
import com.lzpeng.project.sys.domain.Notice;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 通知 数据层
 *
 * @author : 李志鹏
 * @date : 2020-5-16
 * @time : 21:03:56
 */
@Api(tags = "通知 Entity")
public interface NoticeRepository extends BaseRepository<Notice> {

    /**
     * 更新通知状态
     *
     * @param id      通知id
     * @param enabled 通知状态
     * @return
     */
    @Override
    @Modifying
    @Query("UPDATE Notice t SET t.enabled = :enabled WHERE t.id = :id")
    int updateEnabled(@Param("id") String id, @Param("enabled") Boolean enabled);


}
