package com.lzpeng.project.tool.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @date: 2020/4/16
 * @time: 14:18
 * @author:   李志鹏
 */
@Data
@AllArgsConstructor(staticName = "create")
public class ClassInfo {

    /**
     * 类
     */
    private Class clazz;

    /**
     * 资源Url
     */
    private String resourceUrl;

    /**
     * classLoader 信息
     */
    private ClassLoaderInfo classLoaderInfo;

    /**
     * 所有类加载器信息
     */
    private List<ClassLoaderInfo> classLoaderInfoList;
}
