package com.lzpeng.project.sys.domain;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lzpeng.common.annotation.Excel;
import com.lzpeng.framework.annotation.BooleanValue;
import com.lzpeng.framework.annotation.GenerateCode;
import com.lzpeng.framework.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * 用户
 * 用户不能直接和部门关联,有些用户在组织架构里,有些不在(比如供应商)
 * 需要加员工信息表,供应商表一对一关联用户
 * 用户表只做登录使用
 * @date: 2020/2/1
 * @time: 21:50
 * @author: 李志鹏
 */
@Data
@Entity
@ApiModel("用户")
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true, exclude={"roles"})
@ToString(callSuper = true)
@GenerateCode(editPage = GenerateCode.PageType.DIALOG)
public class User extends BaseEntity implements UserDetails {
    /**
     * 用户名 账号
     */
    @NotBlank
    @Excel(name = "用户名")
    @ApiModelProperty("用户名")
    @Column(columnDefinition="varchar(255) COMMENT '用户名'", unique = true)
    private String username;
    /**
     * 密码
     */
    @Size(min = 6, message = "密码最少为6位")
    @ApiModelProperty("密码")
    @Column(columnDefinition="varchar(255) COMMENT '密码'")
    private String password;
    /**
     * 手机号
     */
    @Excel(name = "手机号")
    @ApiModelProperty(value = "手机号", example = "137xxxxxxxx")
    @Column(columnDefinition="varchar(255) COMMENT '手机号'")
    private String mobile;
    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "邮箱", example = "xxxxxxx@qq.com")
    @Column(columnDefinition="varchar(255) COMMENT '邮箱'")
    private String email;
    /**
     * 用户头像
     */
    @Excel(name = "头像")
    @ApiModelProperty(value = "用户头像", example = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
    @Column(columnDefinition="varchar(255) DEFAULT 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif' COMMENT '用户头像'")
    private String avatar;
    /**
     * 用户真实姓名
     */
    @Excel(name = "姓名")
    @ApiModelProperty(value = "用户真实姓名", example = "张三")
    @Column(columnDefinition="varchar(255) COMMENT '用户真实姓名'")
    private String name;
    /**
     * 用户自我介绍
     */
    @Excel(name = "自我介绍")
    @ApiModelProperty(value = "用户自我介绍")
    @Column(columnDefinition="varchar(255) COMMENT '用户自我介绍'")
    private String introduction;
    /**
     * 上次登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "最后登录时间", hidden = true)
    @Column(columnDefinition="datetime COMMENT '最后登录时间'")
    private Date lastLoginTime;

    /**
     * 角色
     */
    @ManyToMany(targetEntity = Role.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @ApiModelProperty(value = "角色", hidden = true)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "varchar(255) COMMENT '用户id'")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id", columnDefinition = "varchar(255) COMMENT '角色id'")})
    private List<Role> roles = new ArrayList<>();


    @Override
    @ApiModelProperty(value = "权限列表", hidden = true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 1. commaSeparatedStringToAuthorityList放入角色时需要加前缀ROLE_，而在controller使用时不需要加ROLE_前缀
        // 2. 放入的是权限时，不能加ROLE_前缀，hasAuthority与放入的权限名称对应即可
        Set<String> authoritySet = new HashSet<>();
        if (roles != null) {
            for (Role role : roles) {
                authoritySet.add("ROLE_".concat(role.getNumber()));
                Collection<Menu> menus = role.getMenus();
                for (Menu menu : menus) {
                    if (!StrUtil.isBlankOrUndefined(menu.getNumber())) {
                        authoritySet.add(menu.getNumber());
                    }
                }
            }
        }
        return AuthorityUtils.createAuthorityList(authoritySet.toArray(new String[authoritySet.size()]));
    }

    /**
     * 账户是否未过期
     */
    @Column(columnDefinition="bit DEFAULT b'1' COMMENT '账户是否未过期'")
    @ApiModelProperty(value = "账户是否未过期", hidden = true)
    @BooleanValue(trueValue = "未过期", falseValue = "已过期")
    private boolean accountNonExpired = true;


    /**
     * 账户是否未锁定(冻结)冻结用户可以恢复
     */
    @Column(columnDefinition="bit DEFAULT b'1' COMMENT '账户是否未锁定(冻结)冻结用户可以恢复'")
    @ApiModelProperty(value = "账户是否未冻结", hidden = true)
    @BooleanValue(trueValue = "未冻结", falseValue = "已冻结")
    private boolean accountNonLocked = true;

    /**
     * 密码是否未过期
     */
    @Column(columnDefinition="bit DEFAULT b'1' COMMENT '密码是否未过期'")
    @ApiModelProperty(value = "密码是否未过期", hidden = true)
    @BooleanValue(trueValue = "未过期", falseValue = "已过期")
    private boolean credentialsNonExpired = true;

    /**
     * 账户是否可用(是否未被删除)删除用户不可恢复
     */
    @Column(columnDefinition="bit DEFAULT b'1' COMMENT '账户是否可用(是否未被删除)删除用户不可恢复'")
    @ApiModelProperty(value = "账户是否未删除", hidden = true)
    @BooleanValue(trueValue = "未删除", falseValue = "已删除")
    private boolean enabled = true;


}
