package com.lzpeng.project.tool.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import com.lzpeng.framework.domain.IntEnum;
import com.lzpeng.framework.domain.AbstractIntEnumConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
     * Java 文件
     */
    JAVA(0, "text/x-java"),
    /**
     * js 文件
     */
    JAVA_SCRIPT(1, "text/javascript"),
    /**
     * VUE 文件
     */
    VUE(2, "text/x-vue");

    @JsonValue
    private Integer code;
    private String message;

    public static class Converter extends AbstractIntEnumConverter<GenFileType> {}
}
