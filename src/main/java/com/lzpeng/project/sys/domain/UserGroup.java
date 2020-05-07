//package com.lzpeng.project.sys.domain;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.lzpeng.common.annotation.Excel;
//import com.lzpeng.framework.annotation.GenerateCode;
//import com.lzpeng.framework.domain.TreeEntity;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.ToString;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * 用户组
// * @date: 2020/4/22
// * @time: 21:50
// * @author: 李志鹏
// */
//@Data
//@Entity
//@ApiModel("用户组")
//@DynamicInsert
//@DynamicUpdate
//@EqualsAndHashCode(callSuper = true)
//@ToString(callSuper = true)
//@GenerateCode(editPage = GenerateCode.PageType.DIALOG)
//public class UserGroup extends TreeEntity<UserGroup> {
//    /**
//     * 用户组名称
//     */
//    @Excel(name = "用户组名称")
//    @ApiModelProperty("用户组名称")
//    @Column(columnDefinition="varchar(255) COMMENT '用户组名称'")
//    private String name;
//
//    /**
//     * 用户组编码
//     */
//    @Excel(name = "用户组编码")
//    @ApiModelProperty("用户组编码")
//    @Column(columnDefinition="varchar(255) COMMENT '用户组编码'", unique = true)
//    private String number;
//
//
//    /**
//     * 角色
//     */
//    @JsonIgnore
//    @OneToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
//    @ApiModelProperty(value = "角色", hidden = true)
//    @JoinColumn(name = "user_group_id", columnDefinition = "varchar(255) COMMENT '用户组id'")
//    private List<Role> roles = new ArrayList<>();
//
//    /**
//     * 用户
//     */
//    @JsonIgnore
//    @ManyToMany(targetEntity = User.class, fetch = FetchType.EAGER)
//    @ApiModelProperty(value = "用户", hidden = true)
//    @JoinTable(name = "user_group_user", joinColumns = {@JoinColumn(name = "user_group_id", referencedColumnName = "id", columnDefinition = "varchar(255) COMMENT '用户组id'")},
//            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "varchar(255) COMMENT '用户id'")})
//    private Set<User> users = new HashSet<>();
//
//}
