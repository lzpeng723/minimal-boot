package com.lzpeng.project.monitor.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import com.lzpeng.framework.domain.IntEnum;
import com.lzpeng.framework.domain.AbstractIntEnumConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @date: 2020/4/9
 * @time: 0:03
 * @author:   李志鹏
 */
@Getter
@AllArgsConstructor
public enum JobType implements IntEnum {
    /**
     * JAVA 任务
     */
    JAVA(0, "JAVA"),
    /**
     * Rhino 脚本任务
     */
    RHINO(1, "Rhino"),
    /**
     * Nashorn脚本任务
     */
    NASHORN(2, "Nashorn");

    @JsonValue
    private Integer code;
    private String message;

    public static class Converter extends AbstractIntEnumConverter<JobType> {}
}
