package com.lzpeng.project.tool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lzpeng.framework.annotation.BooleanValue;
import com.lzpeng.framework.annotation.GenerateCode;
import com.lzpeng.framework.domain.BaseEntity;
import com.lzpeng.framework.domain.IntEnum;
import com.lzpeng.framework.domain.AbstractIntEnumConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2020/3/18
 * @time: 18:04
 * @author:   李志鹏
 * 表中的列信息
 */
@Data
@Entity
@ApiModel("字段信息")
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = false)
@GenerateCode(generate = false)
@ToString(callSuper = true, exclude = {"table"})
public class ColumnInfo extends BaseEntity {

    /** 所在的表 */
    @JsonIgnore
    @ApiModelProperty("所在的表")
    @ManyToOne(targetEntity = TableInfo.class,cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private TableInfo tableInfo;

    /** 列名 */
    @ApiModelProperty("字段名")
    @Column(columnDefinition="varchar(255) COMMENT '字段名'", nullable = false)
    private String columnName;

    /** 类型，对应java.sql.Types中的类型 */
    @ApiModelProperty("字段类型")
    @Column(columnDefinition="int(11) COMMENT '对应java.sql.Types中的类型'", nullable = false)
    private Integer type;

    /** 类型名称 */
    @ApiModelProperty("字段类型")
    @Column(columnDefinition="varchar(255) COMMENT '字段类型'", nullable = false)
    private String typeName;

    /** 大小或数据长度 */
    @ApiModelProperty("大小或数据长度")
    @Column(columnDefinition="int(11) COMMENT '大小或数据长度'")
    private Integer size;

    /** 是否为可空 */
    @ApiModelProperty("是否为可空")
    @Column(columnDefinition="bit COMMENT '是否为可空'")
    @BooleanValue(trueValue = "字段不可以为空", falseValue = "字段可以为空")
    private Boolean nullable;

    /** 注释 */
    @ApiModelProperty("注释")
    @Column(columnDefinition="varchar(255) COMMENT '注释'")
    private String comment;

    /** 实体属性名称 */
    @ApiModelProperty("实体属性名称")
    @Column(columnDefinition="varchar(255) COMMENT '实体属性名称'")
    private String fieldName;

    /** 实体属性类型 */
    @ApiModelProperty("实体属性类型")
    @Column(columnDefinition="varchar(255) COMMENT '实体属性类型'")
    private String className;

    /** 实体属性上注解ApiModelProperty的value */
    @ApiModelProperty("实体属性别名")
    @Column(columnDefinition="varchar(255) COMMENT '实体属性上注解ApiModelProperty的value'")
    private String apiModelProperty;

    /** 前端用什么组件展示 默认el-input*/
    @ApiModelProperty("组件名称")
    @Column(columnDefinition="varchar(255) COMMENT '前端用什么组件展示 默认el-input'")
    private String componentName;


    @ApiModelProperty("取值范围")
    @OneToMany(targetEntity = DictValue.class,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "column_info_id", columnDefinition = "varchar(255) COMMENT '字段信息id'")
    private List<DictValue> dictValues = new ArrayList<>();


    /**
     * 生成文件类型
     * @date: 2020/4/13
     * @time: 22:01
     * @author:   李志鹏
     */
    @Getter
    @AllArgsConstructor
    public enum GenFileType implements IntEnum {
        /**
         * 生成JAVA文件
         */
        JAVA(0, "text/x-java"),
        /**
         * 生成JS文件
         */
        JAVA_SCRIPT(1, "text/javascript"),
        /**
         * 生成Vue文件
         */
        VUE(2, "text/x-vue");

        @JsonValue
        private Integer code;
        private String message;

        public static class Converter extends AbstractIntEnumConverter<GenFileType> {}
    }
}
