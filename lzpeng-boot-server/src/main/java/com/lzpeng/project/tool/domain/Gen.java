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

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 代码生成模板配置
 * @date: 2020/4/12
 * @time: 22:33
 * @author:   李志鹏
 */
@Data
@Entity
@ApiModel("代码生成模板")
@DynamicUpdate
@DynamicInsert
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@GenerateCode(editPage = GenerateCode.PageType.DETAIL)
public class Gen extends BaseEntity {


    @ApiModelProperty("模板名称")
    @Column(columnDefinition="varchar(255) COMMENT '模板名称'")
    private String name;


    @ApiModelProperty("模板编码")
    @Column(columnDefinition="varchar(255) COMMENT '模板编码'", unique = true)
    private String number;

    @ApiModelProperty("生成路径")
    @Column(columnDefinition="varchar(255) COMMENT '生成路径'")
    private String path;

    @ApiModelProperty("模板文件")
    @Column(columnDefinition="text COMMENT '模板文件'")
    private String template;

    @ApiModelProperty("默认是否覆盖生成")
    @Column(columnDefinition="bit DEFAULT b'0' COMMENT '默认是否覆盖生成'")
    private Boolean override;

    @ApiModelProperty("生成文件类型")
    @Convert(converter = com.lzpeng.project.tool.domain.GenFileType.Converter.class)
    @Column(columnDefinition="int(11) COMMENT '生成文件类型 java js vue'", nullable = false)
    private GenFileType type;

}
