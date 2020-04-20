package com.lzpeng.project.sys.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lzpeng.common.annotation.Excel;
import com.lzpeng.framework.annotation.BooleanValue;
import com.lzpeng.framework.annotation.GenerateCode;
import com.lzpeng.framework.domain.TreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

/**
 * 菜单
 * @date: 2020/2/1
 * @time: 22:10
 * @author: 李志鹏
 */
@Data
@Entity
@ApiModel("权限菜单")
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true, exclude={"parent", "roles"})
@ToString(callSuper = true, exclude={"parent", "roles"})
@GenerateCode(editPage = GenerateCode.PageType.DIALOG)
public class Menu extends TreeEntity<Menu> {

    /**
     * 权限菜单名称
     */
    
    @Excel(name = "菜单名称")
    @ApiModelProperty("权限菜单名称")
    @Column(columnDefinition="varchar(255) COMMENT '权限菜单名称'")
    private String name;

    /**
     * 权限编码
     */
    @Excel(name = "权限编码")
    @ApiModelProperty("权限编码")
    @Column(columnDefinition="varchar(255) COMMENT '权限编码'", unique = true)
    private String number;

    // VUE-ELEMENT-ADMIN 使用 字段名称不可修改
    @Excel(name = "路由地址")
    @ApiModelProperty("菜单对应前端路由")
    @Column(columnDefinition="varchar(255) COMMENT '菜单对应前端路由'")
    private String path;

    // VUE-ELEMENT-ADMIN 使用 字段名称不可修改
    @Excel(name = "重定向路由")
    @ApiModelProperty("菜单重定向路由")
    @Column(columnDefinition="varchar(255) COMMENT '重定向路由'")
    private String redirect;

    // VUE-ELEMENT-ADMIN 使用 字段名称不可修改
    @Excel(name = "是否隐藏")
    @ApiModelProperty("按钮菜单是否隐藏")
    @Column(columnDefinition="bit DEFAULT b'0' COMMENT '是否隐藏'")
    @BooleanValue(trueValue = "隐藏", falseValue = "显示")
    private Boolean hidden;

    // VUE-ELEMENT-ADMIN 使用 字段名称不可修改
    @Excel(name = "永远显示")
    @ApiModelProperty("当下级只有一个子路由时是否显示菜单")
    @Column(columnDefinition="bit DEFAULT b'0' COMMENT '当下级只有一个子路由时是否显示'")
    @BooleanValue(trueValue = "显示", falseValue = "隐藏")
    private Boolean alwaysShow;

    // VUE-ELEMENT-ADMIN 使用 字段名称不可修改
    @Excel(name = "Vue组件")
    @ApiModelProperty("菜单对应Vue组件路径")
    @Column(columnDefinition="varchar(255) COMMENT '对应Vue组件路径'")
    private String component;


    @Excel(name = "是否外链")
    @ApiModelProperty("是否外链")
    @Column(columnDefinition="bit DEFAULT b'0' COMMENT '是否外链 默认否'")
    @BooleanValue
    private Boolean frame;


    @Excel(name = "功能url")
    @ApiModelProperty("按钮对应后端功能url")
    @Column(columnDefinition="varchar(255) COMMENT '按钮对应后端功能url'")
    private String function;


    @Excel(name = "菜单类型")
    @ApiModelProperty("权限菜单类型")
    @Column(columnDefinition="int(11) COMMENT '菜单类型 0: 目录, 1: 菜单, 2: 功能'", nullable = false)
    @Convert(converter = MenuType.Converter.class)
    private MenuType type;

    // VUE-ELEMENT-ADMIN 使用 字段名称不可修改
    @Excel(name = "变体")
    @ApiModelProperty("标题")
    @Column(columnDefinition = "varchar(255) COMMENT '标题'")
    private String title;

    // VUE-ELEMENT-ADMIN 使用 字段名称不可修改
    @Excel(name = "图标")
    @ApiModelProperty("图标")
    @Column(columnDefinition = "varchar(255) COMMENT '图标'")
    private String icon;

    // VUE-ELEMENT-ADMIN 使用 字段名称不可修改
    @Excel(name = "是否缓存")
    @ApiModelProperty("是否缓存")
    @Column(columnDefinition = "bit DEFAULT b'0' COMMENT '是否缓存 0: 缓存 1: 不缓存'")
    @BooleanValue(trueValue = "否", falseValue = "是")
    private Boolean noCache;

    // VUE-ELEMENT-ADMIN 使用 字段名称不可修改
    @Excel(name = "固定在tagsView")
    @ApiModelProperty("是否固定在tagsView")
    @Column(columnDefinition = "bit DEFAULT b'0' COMMENT '是否固定在tagsView 0: 不固定 1: 固定'")
    @BooleanValue
    private Boolean affix;

    // VUE-ELEMENT-ADMIN 使用 字段名称不可修改
    @Excel(name = "在面包屑中显示")
    @ApiModelProperty("是否在面包屑中显示")
    @Column(columnDefinition = "bit DEFAULT b'1' COMMENT '是否在面包屑中显示'")
    @BooleanValue
    private Boolean breadcrumb;

    // VUE-ELEMENT-ADMIN 使用 字段名称不可修改
    @Excel(name = "高亮路由")
    @ApiModelProperty("高亮显示路由")
    @Column(columnDefinition = "varchar(255) COMMENT '高亮显示路由'")
    private String activeMenu;

    //@OneToOne(targetEntity = MenuMeta.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    //@JoinColumn(name = "meta_id", columnDefinition = "varchar(255) COMMENT '菜单详细信息id'")
    @Transient
    private MenuMeta meta;

    @JsonIgnore
    @ApiModelProperty(value = "角色", hidden = true)
    @ManyToMany(mappedBy = "menus")
    private Set<Role> roles;

}
