package com.lzpeng.project.sys.repository;

import com.lzpeng.framework.web.repository.BaseRepository;
import com.lzpeng.project.sys.domain.NotificationRecord;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 通知记录 数据层
 *
 * @author : 李志鹏
 * @date : 2020-5-17
 * @time : 0:43:53
 */
@Api(tags = "通知记录 Entity")
public interface NotificationRecordRepository extends BaseRepository<NotificationRecord> {

    /**
     * 更新通知记录状态
     *
     * @param id      通知记录id
     * @param enabled 通知记录状态
     * @return
     */
    @Override
    @Modifying
    @Query("UPDATE NotificationRecord t SET t.enabled = :enabled WHERE t.id = :id")
    int updateEnabled(@Param("id") String id, @Param("enabled") Boolean enabled);


}
