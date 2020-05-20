package com.lzpeng.project.sys.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import com.lzpeng.framework.domain.IntEnum;
import com.lzpeng.framework.domain.AbstractIntEnumConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型
 * @date: 2020/4/5
 * @time: 1:16
 * @author:   李志鹏
 */
@Getter
@AllArgsConstructor
public enum MenuType implements IntEnum {
    /**
     * 菜单组
     */
    CONTENT(0, "目录"),
    /**
     * 菜单项
     */
    MENU(1, "菜单"),
    /**
     * 功能
     */
    FUNCTION(2, "功能");

    @JsonValue
    private Integer code;
    private String message;

    public static class Converter extends AbstractIntEnumConverter<MenuType> {}
}
