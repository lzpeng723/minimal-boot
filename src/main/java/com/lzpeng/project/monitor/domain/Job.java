package com.lzpeng.project.monitor.domain;

import com.lzpeng.common.annotation.Excel;
import com.lzpeng.framework.annotation.GenerateCode;
import com.lzpeng.framework.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;

/**
 * @date: 2020/4/6
 * @time: 0:40
 * @author:   李志鹏
 * 方法日志
 */
@Data
@Entity
@ApiModel("定时任务")
@DynamicUpdate
@DynamicInsert
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@GenerateCode(editPage = GenerateCode.PageType.DETAIL)
public class Job extends BaseEntity {

    /**
     * 任务名称
     */
    @Excel(name = "任务名称")
    @ApiModelProperty("任务名称")
    @Column(columnDefinition="varchar(255) COMMENT '任务名称'")
    private String name;

    /**
     * 任务编码
     */
    @Excel(name = "任务编码")
    @ApiModelProperty("任务编码")
    @Column(columnDefinition="varchar(255) COMMENT '任务编码'", unique = true)
    private String number;

    /**
     * 任务分组
     */
    @Excel(name = "任务分组")
    @ApiModelProperty("任务分组")
    @Column(name = "_group", columnDefinition="varchar(255) COMMENT '任务分组'")
    private String group;


    /**
     * 执行时间 ms
     */
    @Excel(name = "类名")
    @ApiModelProperty("类名")
    @Column(columnDefinition="varchar(255) COMMENT '类名'")
    private Class<? extends QuartzJobBean> clazz;


    /**
     * 任务计划
     */
    @Excel(name = "任务计划")
    @ApiModelProperty("任务计划")
    @Column(columnDefinition="varchar(255) COMMENT '任务计划'")
    private String cron;

    /**
     * 执行脚本
     */
    @ApiModelProperty("执行脚本")
    @Column(columnDefinition="text COMMENT '执行脚本'")
    private String script;

    /**
     * 定时任务类型
     */
    @Excel(name = "定时任务类型")
    @ApiModelProperty("定时任务类型")
    @Convert(converter = JobType.Converter.class)
    @Column(columnDefinition="int(11) COMMENT '定时任务类型 JAVA Rhino Nashorn'")
    private JobType type;
    /**
     * 定时任务状态
     */
    @Excel(name = "定时任务形态")
    @ApiModelProperty("定时任务状态")
    @Convert(converter = JobStatus.Converter.class)
    @Column(columnDefinition="int(11) DEFAULT 0 COMMENT '定时任务状态 未启用 已启用 已暂停'")
    private JobStatus jobStatus;

}
