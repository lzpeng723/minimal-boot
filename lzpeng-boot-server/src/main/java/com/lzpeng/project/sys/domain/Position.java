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

/**
 * @date: 2020/3/20
 * @time: 15:52
 * @author:   李志鹏
 */
@Data
@Entity
@ApiModel("岗位")
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@GenerateCode(editPage = GenerateCode.PageType.DIALOG)
public class Position extends LeftTreeRightTableEntity<Department> {

    /**
     * 岗位名称
     */
    @Excel(name = "岗位名称")
    @ApiModelProperty("岗位名称")
    @Column(columnDefinition="varchar(255) COMMENT '岗位名称'")
    private String name;

    /**
     * 岗位编码
     */
    @Excel(name = "岗位编码")
    @ApiModelProperty("岗位编码")
    @Column(columnDefinition="varchar(255) COMMENT '岗位编码'", unique = true)
    private String number;

//    /**
//     * 此处 fetch 不能为 EAGER
//     * hibernate一对多连接懒加载EAGER只能有一个，如果有多个报错，需要手动强制录入
//     */
//    @ApiModelProperty("用户信息")
//    @OneToMany(targetEntity = User.class, fetch = FetchType.LAZY)
//    @JoinColumn(name = "position_id", columnDefinition = "varchar(255) COMMENT '岗位id'")
//    private Set<User> users = new HashSet<>();

//    @JsonIgnore
//    @ApiModelProperty("所属部门")
//    @ManyToOne(targetEntity = Department.class, fetch = FetchType.LAZY)
//    private Department department;

    /**
     * 一个岗位一定对应一个角色
     * 但是一个角色不一定对应一个岗位,可能属于某用户组
     */
    @JsonIgnore
    @ApiModelProperty("角色")
    @OneToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", columnDefinition = "varchar(255) COMMENT '角色id'")
    private Role role;
}
