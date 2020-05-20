package com.lzpeng.project.sys.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import com.lzpeng.framework.domain.IntEnum;
import com.lzpeng.framework.domain.AbstractIntEnumConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @date: 2020/4/5
 * @time: 2:22
 * @author:   李志鹏
 */
@Getter
@AllArgsConstructor
public enum DataScopeType implements IntEnum {

    /**
     * 仅本人数据权限
     */
    SELF(0, "仅本人数据权限"),
    /**
     * 本部门数据权限
     */
    DEPARTMENT(1, "本部门数据权限"),
    /**
     * 本部门及以下数据权限
     */
    DEPARTMENT_SUB(2, "本部门及以下数据权限"),
    /**
     * 全部数据权限
     */
    ALL(3, "全部数据权限"),
    /**
     * 自定义数据权限
     */
    CUSTOM(4, "自定义数据权限");

    @JsonValue
    private Integer code;
    private String message;

    public static class Converter extends AbstractIntEnumConverter<DataScopeType> {}
}
