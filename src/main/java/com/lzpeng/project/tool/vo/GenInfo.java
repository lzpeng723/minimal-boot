package com.lzpeng.project.tool.vo;

import lombok.Data;

/**
 * 代码生成情况VO
 * @date: 2020/4/14
 * @time: 22:58
 * @author:   李志鹏
 */
@Data
public class GenInfo {
    /**
     * 代码模板id
     */
    private String id;
    /**
     * 代码模板编码
     */
    private String number;
    /**
     * 代码模板名称
     */
    private String name;

    /**
     * 代码生成情况
     */
    private String gen;
    /**
     * 对应实体类
     */
    private Class<?> clazz;
}
