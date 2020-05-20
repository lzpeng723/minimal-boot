package com.lzpeng.project.sys.domain;

import com.lzpeng.framework.annotation.BooleanValue;
import com.lzpeng.framework.annotation.GenerateCode;
import com.lzpeng.framework.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;

/**
 * @date: 2020/3/13
 * @time: 20:21
 * @author:   李志鹏
 */
@Data
//@Entity
@ApiModel("菜单详细信息")
//@DynamicInsert
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@GenerateCode(generate = false)
public class MenuMeta extends BaseEntity {

    
    @ApiModelProperty("标题")
    @Column(columnDefinition = "varchar(255) COMMENT '标题'")
    private String title;

    
    @ApiModelProperty("图标")
    @Column(columnDefinition = "varchar(255) COMMENT '图标'")
    private String icon;

    
    @ApiModelProperty("是否缓存")
    @Column(columnDefinition = "bit DEFAULT b'0' COMMENT '是否缓存 0: 缓存 1: 不缓存'")
    @BooleanValue(trueValue = "否", falseValue = "是")
    private Boolean noCache;

    
    @ApiModelProperty("是否固定在tagsView")
    @Column(columnDefinition = "bit DEFAULT b'0' COMMENT '是否固定在tagsView 0: 不固定 1: 固定'")
    @BooleanValue
    private Boolean affix;

    
    @ApiModelProperty("是否在面包屑中显示")
    @Column(columnDefinition = "bit DEFAULT b'1' COMMENT '是否在面包屑中显示'")
    @BooleanValue
    private Boolean breadcrumb;

    
    @ApiModelProperty("高亮显示路由")
    @Column(columnDefinition = "varchar(255) COMMENT '高亮显示路由'")
    private String activeMenu;
}
