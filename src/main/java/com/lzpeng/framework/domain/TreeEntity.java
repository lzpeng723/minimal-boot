package com.lzpeng.framework.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽象树形结构
 * @date: 2020/3/14
 * @time: 23:45
 * @author:   李志鹏
 */
@Data
@MappedSuperclass
@DynamicInsert
@EqualsAndHashCode(callSuper = true, exclude={"parent"})
@ToString(callSuper = true, exclude={"parent"})
@JsonIgnoreProperties({"hibernateLnazyInitializer", "handler"})
public abstract class TreeEntity<T extends TreeEntity<T>> extends BaseEntity {

    @ApiModelProperty("顺序号")
    @Column(columnDefinition="int(11) COMMENT '顺序号'", nullable = false)
    private Integer orderNum;

    /**
     * 父节点
     * cascade 关系维护端
     */
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},  fetch = FetchType.LAZY)
    @ApiModelProperty(value=  "父节点", hidden=true)
    @JoinColumn(name = "parent_id", columnDefinition = "varchar(255) COMMENT '父级id'")
    private T parent;

    /**
     * 级联删除子节点
     * mappedBy 关系被维护端
     * 因为不是关系维护端
     * 所以不能加 @JoinColumn 注解
     */
    @OneToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER, mappedBy = "parent")
    @ApiModelProperty(value = "子节点", hidden=true)
    private List<T> children = new ArrayList<>();


    /**
     * 父节点 Id 不存数据库,接收前台参数使用
     */
    @Transient
    @ApiModelProperty("父节点id")
    private String parentId;


}
