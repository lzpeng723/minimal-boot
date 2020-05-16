package com.lzpeng.project.sys.domain;

import com.lzpeng.framework.annotation.BooleanValue;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 通知发送记录
 *
 * @date: 2020/5/16
 * @time: 20:52
 * @author: 李志鹏
 */
@Data
@Entity
@ApiModel("通知记录")
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NotificationRecord extends BaseEntity {

    /**
     * 发送者
     */
    @ManyToOne
    @ApiModelProperty(value = "发送者", hidden = true)
    @JoinColumn(name = "sender_id", columnDefinition = "varchar(255) COMMENT '发送者id'")
    private User sender;

    /**
     * 通知
     */
    @ManyToOne
    @ApiModelProperty(value = "通知", hidden = true)
    @JoinColumn(name = "notice_id", columnDefinition = "varchar(255) COMMENT '通知id'")
    private Notice notice;

    /**
     * 接收者
     */
    @ManyToOne
    @ApiModelProperty(value = "接收者", hidden = true)
    @JoinColumn(name = "receiver_id", columnDefinition = "varchar(255) COMMENT '接收者id'")
    private User receiver;

    /**
     * 是否已读
     */
    @ApiModelProperty(value = "是否已读")
    @BooleanValue(trueValue = "已读", falseValue = "未读")
    @Column(columnDefinition = "bit DEFAULT b'0' COMMENT '是否已读 0: 未读, 1: 已读'")
    private Boolean view;

}
