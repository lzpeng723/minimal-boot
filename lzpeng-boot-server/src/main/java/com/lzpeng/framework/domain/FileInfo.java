package com.lzpeng.framework.domain;

import com.lzpeng.framework.annotation.GenerateCode;
import com.lzpeng.framework.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 文件上传信息
 * @date: 2020/3/10
 * @time: 23:01
 * @author:   李志鹏
 */
@Data
@Entity
@ApiModel("文件信息")
@ToString(callSuper = true)
@GenerateCode(generate = false)
public class FileInfo extends BaseEntity {
    /**
     * 文件名
     */
    @ApiModelProperty("文件名")
    @Column(columnDefinition="varchar(255) COMMENT '文件名'")
    private String fileName;

    /**
     * 文件路径
     */
    @ApiModelProperty("文件路径")
    @Column(columnDefinition="varchar(255) COMMENT '文件路径'")
    private String path;

    /**
     * 原始文件名
     */
    @ApiModelProperty("原始文件名")
    @Column(columnDefinition="varchar(255) COMMENT '原始文件名'")
    private String originalFileName;

    /**
     * 文件字节数
     */
    @ApiModelProperty("文件字节数")
    @Column(columnDefinition="bigint COMMENT '文件字节数'")
    private Long size;
    /**
     * contentType
     */
    @ApiModelProperty("contentType")
    @Column(columnDefinition="varchar(255) COMMENT 'contentType'")
    private String contentType;
    /**
     * 文件后缀名
     */
    @ApiModelProperty("文件后缀名")
    @Column(columnDefinition="varchar(255) COMMENT '文件后缀名'")
    private String extension;
}
