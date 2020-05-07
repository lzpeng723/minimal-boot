package com.lzpeng.framework.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

/**
 * 左树右表之右表entity
 * @date: 2020/5/2
 * @time: 21:51
 * @author: 李志鹏
 */
@Data
@MappedSuperclass
@DynamicInsert
@EqualsAndHashCode(callSuper = true, exclude={"tree"})
@ToString(callSuper = true, exclude={"tree"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LeftTreeRightTableEntity<T extends TreeEntity<T>> extends BaseEntity {
    /**
     * 树节点id
     */
    @Transient
    @ApiModelProperty("树节点id")
    private String treeId;

    /**
     * 树节点
     * cascade 关系维护端
     */
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @ApiModelProperty(value=  "树节点", hidden=true)
    @JoinColumn(name = "tree_id", columnDefinition = "varchar(255) COMMENT '树节点id'")
    private T tree;
}
