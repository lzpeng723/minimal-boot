package com.lzpeng.project.tool.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 返回给前台类加载器信息
 * @date: 2020/4/16
 * @time: 13:45
 * @author:   李志鹏
 */
@Data
@AllArgsConstructor(staticName = "create")
public class ClassLoaderInfo {
    /**
     * 类加载器
     */
    @JsonIgnore
    private ClassLoader classLoader;
    /**
     * 类加载器名称
     */
    private String name;
    /**
     * 类加载器加载的classPath
     */
    private String[] classPath;
}
