package com.lzpeng.project.monitor.domain;

import com.lzpeng.common.annotation.Excel;
import com.lzpeng.framework.annotation.BooleanValue;
import com.lzpeng.framework.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @date: 2020/4/6
 * @time: 0:40
 * @author:   李志鹏
 * 请求日志
 */
@Data
@Entity
@ApiModel("请求日志")
@DynamicUpdate
@DynamicInsert
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RequestLog extends BaseEntity {
    /**
     * 用户名
     */
    @Excel(name = "用户名", imported = false)
    @ApiModelProperty("用户名")
    @Column(columnDefinition="varchar(255) COMMENT '用户名'")
    private String username;

    /**
     * 请求url
     */
    @Excel(name = "请求url", imported = false)
    @ApiModelProperty("请求url")
    @Column(columnDefinition="varchar(255) COMMENT '请求url'")
    private String url;

    /**
     * 请求uri,即去掉http(s)://host:port之后的url
     */
    @Excel(name = "请求uri", imported = false)
    @ApiModelProperty("请求uri")
    @Column(columnDefinition="varchar(255) COMMENT '请求uri,即去掉http(s)://host:port之后的url'")
    private String uri;


    /**
     * 本次请求耗费时间
     */
    @Excel(name = "耗费时间", imported = false)
    @ApiModelProperty("请求耗费时间")
    @Column(columnDefinition="bigint(20) COMMENT '请求耗费时间'")
    private Long costTime;

    /**
     * ?之后参数
     */
    @Excel(name = "查询参数", imported = false)
    @ApiModelProperty("query参数")
    @Column(columnDefinition="varchar(255) COMMENT 'query参数'")
    private String queryString;

    /**
     * 客户端ip
     */
    @Excel(name = "客户端ip", imported = false)
    @ApiModelProperty("客户端ip")
    @Column(columnDefinition="varchar(255) COMMENT '客户端ip'")
    private String ip;

    /**
     * 客户端用户名
     */
    @Excel(name = "客户端用户名", imported = false)
    @ApiModelProperty("客户端用户名")
    @Column(columnDefinition="varchar(255) COMMENT '客户端用户名'")
    private String remoteUser;

    /**
     * 请求方法
     * {@link org.springframework.http.HttpHeaders}
     */
    @Excel(name = "请求方法", imported = false)
    @ApiModelProperty("请求方法")
    @Column(columnDefinition="varchar(255) COMMENT '请求方法'")
    private String method;

    /**
     * 请求是否成功
     */
    @ApiModelProperty("请求是否成功")
    @Column(columnDefinition="bit COMMENT '请求是否成功'")
    @BooleanValue(trueValue = "成功", falseValue = "失败")
    private Boolean success;

    /**
     * 请求执行的 java 方法定义
     */
    @Excel(name = "java方法定义", imported = false)
    @ApiModelProperty("请求执行的 java 方法定义")
    @Column(columnDefinition="varchar(255) COMMENT '请求执行的 java 方法定义'")
    private String methodDef;

    /**
     * 请求中 User-Agent 信息
     */
    @Excel(name = "设备信息", imported = false)
    @ApiModelProperty("设备信息")
    @OneToOne(targetEntity = UA.class, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "ua_id", columnDefinition = "varchar(255) COMMENT 'User-Agent id'")
    private UA ua;
}
