package com.lzpeng.project.tool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

import javax.persistence.*;

/**
 * @date: 2020/3/18
 * @time: 12:15
 * @author:   李志鹏
 * 字典类型
 */
@Data
@Entity
@ApiModel("字典信息")
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@GenerateCode(generate = false)
@NamedQuery(name = "findAll", query = "SELECT dv FROM DictValue dv ORDER BY dv.key")
public class DictValue extends BaseEntity {


    /** 哪个列使用 */
    @JsonIgnore
    @ApiModelProperty("列信息")
    @ManyToOne(targetEntity = com.lzpeng.project.tool.domain.ColumnInfo.class,cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private ColumnInfo columnInfo;
    /**
     * 数据库中存什么值
     */
    @ApiModelProperty("数据库中存的值")
    @Column(name = "_key", columnDefinition="int(11) COMMENT '数据库中存的值'")
    private Integer key;

    /**
     * 前端展示什么值
     */
    @ApiModelProperty("前端展示的值")
    @Column(name = "_value", columnDefinition="varchar(255) COMMENT '前端展示的值'")
    private String value;

    /**
     * 顺序
     */
    @ApiModelProperty("顺序号")
    @Column(columnDefinition="int(11) COMMENT '顺序号'")
    private Integer orderNum;

    /**
     * 是否默认
     */
    @ApiModelProperty("是否默认")
    @Column(columnDefinition="bit DEFAULT b'0' COMMENT '是否默认'")
    @BooleanValue(trueValue = "是字段默认值", falseValue = "不是字段默认值")
    private Boolean defaulted;

}
