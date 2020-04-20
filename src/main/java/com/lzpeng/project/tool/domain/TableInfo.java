package com.lzpeng.project.tool.domain;

import com.lzpeng.framework.annotation.GenerateCode;
import com.lzpeng.framework.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @date: 2020/3/18
 * @time: 17:53
 * @author:   李志鹏
 * 项目中所用的的表信息
 */
@Data
@Entity
@ApiModel("表信息")
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = false, exclude={"columns"})
@ToString(callSuper = true)
@GenerateCode(editPage = GenerateCode.PageType.DETAIL)
public class TableInfo extends BaseEntity {

    /** 表名 */
    @ApiModelProperty("表名")
    @Column(columnDefinition="varchar(255) COMMENT '表名'", nullable = false, unique = true)
    private String tableName;

    /** 注释 */
    @ApiModelProperty("表注释")
    @Column(columnDefinition="varchar(255) COMMENT '表注释'")
    private String comment;

    /** 实体类型 */
    @ApiModelProperty("实体类名")
    @Column(columnDefinition="varchar(255) COMMENT '实体类名'", nullable = false, unique = true)
    private String className;

    /** 实体类上注解ApiModel的value */
    @ApiModelProperty("实体类别名")
    @Column(columnDefinition="varchar(255) COMMENT '实体类上注解ApiModel的value'")
    private String apiModel;

    @ApiModelProperty("列信息")
    @OneToMany(targetEntity = ColumnInfo.class,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "table_info_id", columnDefinition = "varchar(255) COMMENT '表信息id'")
    private Set<ColumnInfo> columns = new HashSet<>();

}
