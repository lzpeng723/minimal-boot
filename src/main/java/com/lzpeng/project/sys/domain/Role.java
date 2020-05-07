package com.lzpeng.project.sys.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lzpeng.common.annotation.Excel;
import com.lzpeng.framework.annotation.GenerateCode;
import com.lzpeng.framework.domain.LeftTreeRightTableEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 角色
 * @date: 2020/2/1
 * @time: 22:09
 * @author: 李志鹏
 */
@Data
@Entity
@ApiModel("角色")
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true, exclude={"users", "menus"})
@ToString(callSuper = true, exclude={"users", "menus"})
@GenerateCode(editPage = GenerateCode.PageType.DIALOG)
public class Role extends LeftTreeRightTableEntity<Department> {

    /**
     * 角色名称
     */
    @Excel(name = "角色名称")
    @ApiModelProperty("角色名称")
    @Column(columnDefinition="varchar(255) COMMENT '角色名称'")
    private String name;

    /**
     * 角色编码
     */
    @Excel(name = "角色编码")
    @ApiModelProperty("角色编码")
    @Column(columnDefinition="varchar(255) COMMENT '角色编码'", unique = true)
    private String number;


    @Excel(name = "介绍")
    @ApiModelProperty(value = "角色介绍", example = "一句话说明这个角色能干什么")
    @Column(columnDefinition="varchar(255) COMMENT '角色介绍'")
    private String description;


    @Excel(name = "数据权限")
    @ApiModelProperty("数据权限")
    @Column(columnDefinition="int(11) DEFAULT 0 COMMENT '数据权限 '", nullable = false)
    @Convert(converter = DataScopeType.Converter.class)
    private DataScopeType dataScope;
    /**
     * 用户
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @ApiModelProperty(value= "用户", hidden=true)
    private Collection<User> users = new ArrayList<>();

    /**
     * 权限 菜单
     */
    @ApiModelProperty(value= "菜单")
    @ManyToMany(targetEntity = Menu.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "role_menu", joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id", columnDefinition = "varchar(255) COMMENT '角色id'")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id", referencedColumnName = "id", columnDefinition = "varchar(255) COMMENT '权限菜单id'")})
    private List<Menu> menus = new ArrayList<>();

    /**
     * 一个岗位一定对应一个角色
     * 但是一个角色不一定对应一个岗位,可能属于某用户组
     */
    @JsonIgnore
    @ApiModelProperty("岗位")
    @OneToOne(mappedBy = "role", fetch = FetchType.LAZY)
    private Position position;

    /**
     * 一个岗位一定对应一个角色
     * 但是一个角色不一定对应一个岗位,可能属于某用户组
     */
//    @JsonIgnore
//    @ApiModelProperty("用户组")
//    @ManyToOne(targetEntity = UserGroup.class, fetch = FetchType.EAGER)
//    private UserGroup userGroup;
}
