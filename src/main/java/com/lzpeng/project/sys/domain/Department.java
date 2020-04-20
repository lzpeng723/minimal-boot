package com.lzpeng.project.sys.domain;

import com.lzpeng.common.annotation.Excel;
import com.lzpeng.framework.annotation.GenerateCode;
import com.lzpeng.framework.domain.TreeEntity;
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
 * @date: 2020/3/20
 * @time: 15:59
 * @author:   李志鹏
 */
@Data
@Entity
@ApiModel("部门")
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true, exclude={"parent"})
@ToString(callSuper = true, exclude={"parent"})
@GenerateCode(editPage = GenerateCode.PageType.DIALOG)
public class Department extends TreeEntity<Department> {

    /**
     * 部门名称
     */
    @Excel(name = "部门名称")
    @ApiModelProperty("部门名称")
    @Column(columnDefinition="varchar(255) COMMENT '部门名称'")
    private String name;

    /**
     * 部门编码
     */
    @Excel(name = "部门编码")
    @ApiModelProperty("部门编码")
    @Column(columnDefinition="varchar(255) COMMENT '部门编码'", unique = true)
    private String number;
}
