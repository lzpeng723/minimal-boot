package com.lzpeng.project.monitor.domain;

import com.lzpeng.common.annotation.Excel;
import com.lzpeng.framework.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @date: 2020/4/6
 * @time: 0:40
 * @author:   李志鹏
 * 方法日志
 */
@Data
@Entity
@ApiModel("方法日志")
@DynamicUpdate
@DynamicInsert
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MethodLog extends BaseEntity {

    /**
     * 参数列表
     */
    @ApiModelProperty("参数列表")
    @Column(columnDefinition="text COMMENT '参数列表'")
    private String parameters;

    /**
     * 执行时间 ms
     */
    @Excel(name = "执行时间(毫秒)", imported = false)
    @ApiModelProperty("方法执行时间 ms")
    @Column(columnDefinition="bigint(20) COMMENT '方法执行时间 ms'")
    private Long costTime;

    /**
     * 方法定义
     */
    @Excel(name = "方法定义", imported = false)
    @ApiModelProperty("方法定义")
    @Column(columnDefinition="varchar(255) COMMENT '方法定义'")
    private String methodDef;

}
