package com.lzpeng.project.monitor.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import com.lzpeng.framework.domain.IntEnum;
import com.lzpeng.framework.domain.AbstractIntEnumConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 定时任务状态
 * @date: 2020/4/9
 * @time: 0:03
 * @author:   李志鹏
 */
@Getter
@AllArgsConstructor
public enum JobStatus implements IntEnum {
    /**
     * 任务未启用
     */
    STOP(0, "未启用"),
    /**
     * 任务正在运行中
     */
    RUNNING(1, "运行中"),
    /**
     * 任务已暂停
     */
    PAUSE(2, "已暂停");

    @JsonValue
    private Integer code;
    private String message;

    public static class Converter extends AbstractIntEnumConverter<JobStatus> {}
}
