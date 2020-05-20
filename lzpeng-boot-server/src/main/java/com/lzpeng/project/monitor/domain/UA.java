package com.lzpeng.project.monitor.domain;

import cn.hutool.http.useragent.UserAgent;
import com.lzpeng.framework.annotation.GenerateCode;
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
 * @time: 1:17
 * @author:   李志鹏
 * 请求中 User-Agent 信息
 */
@Data
@Entity
@ApiModel("客户端设备")
@DynamicUpdate
@DynamicInsert
@GenerateCode(generate = false)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UA extends BaseEntity {

    /** 是否为移动平台 */
    @ApiModelProperty("是否为移动平台")
    @Column(columnDefinition="bit COMMENT '是否为移动平台'")
    private boolean mobile;
    /** 浏览器类型 */
    @ApiModelProperty("浏览器类型")
    @Column(columnDefinition="varchar(255) COMMENT '浏览器类型'")
    private String browser;
    /** 平台类型 */
    @ApiModelProperty("平台类型")
    @Column(columnDefinition="varchar(255) COMMENT '平台类型'")
    private String platform;
    /** 系统类型 */
    @ApiModelProperty("系统类型")
    @Column(columnDefinition="varchar(255) COMMENT '系统类型'")
    private String os;
    /** 引擎类型 */
    @ApiModelProperty("引擎类型")
    @Column(columnDefinition="varchar(255) COMMENT '引擎类型'")
    private String engine;
    /** 浏览器版本 */
    @ApiModelProperty("浏览器版本")
    @Column(columnDefinition="varchar(255) COMMENT '浏览器版本'")
    private String browserVersion;
    /** 引擎版本 */
    @ApiModelProperty("引擎版本")
    @Column(columnDefinition="varchar(255) COMMENT '引擎版本'")
    private String engineVersion;

    public static UA create(UserAgent userAgent){
        UA ua = new UA();
        if (userAgent != null) {
            ua.mobile = userAgent.isMobile();
            ua.browser = userAgent.getBrowser().getName();
            ua.platform = userAgent.getPlatform().getName();
            ua.os = userAgent.getOs().getName();
            ua.engine = userAgent.getEngine().getName();
            ua.browserVersion = userAgent.getVersion();
            ua.engineVersion = userAgent.getEngineVersion();
        }
        return ua;
    }
}
