package com.lzpeng.project.sys.domain;

import com.lzpeng.common.annotation.Excel;
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
public class Position extends BaseEntity {

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
}
