package com.lzpeng.project.monitor.repository;

import com.lzpeng.framework.web.repository.BaseRepository;
import com.lzpeng.project.monitor.domain.Job;
import com.lzpeng.project.monitor.domain.JobStatus;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 定时任务 数据层
 * @date: 2020-4-6
 * @time: 17:39:39
 * @author: 李志鹏
 */
@Api(tags = "定时任务 Entity")
public interface JobRepository extends BaseRepository<Job> {

    /**
     * 更新单据状态
     * @param id 单据id
     * @param enabled 单据状态
     * @return
     */
    @Override
    @Modifying
    @Query("UPDATE Job t SET t.enabled = :enabled WHERE t.id = :id")
    int updateEnabled(@Param("id") String id, @Param("enabled") Boolean enabled);

    /**
     * 更新任务状态
     * @param id 单据id
     * @param jobStatus 任务状态
     * @return
     */
    @Modifying
    @Query("UPDATE Job t SET t.jobStatus = :jobStatus WHERE t.id = :id")
    int updateJobStatus(@Param("id") String id, @Param("jobStatus") JobStatus jobStatus);
}
