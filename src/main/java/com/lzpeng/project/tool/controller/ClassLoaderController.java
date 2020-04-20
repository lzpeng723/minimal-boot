package com.lzpeng.project.tool.controller;

import com.lzpeng.common.response.Result;
import com.lzpeng.common.response.ResultUtil;
import com.lzpeng.project.tool.service.ClassLoaderService;
import com.lzpeng.project.tool.vo.ClassInfo;
import com.lzpeng.project.tool.vo.ClassLoaderInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 类加载信息 查看
 * @date: 2020/4/16
 * @time: 12:44
 * @author:   李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/tool/classLoader")
@Api(tags = "类加载接口", value = "查看类以及类加载器信息")
public class ClassLoaderController {

    private static final String MODULE_NAME = "tool";
    private static final String CLASS_NAME = "classLoader";
    private static final String LIST_PERM = MODULE_NAME + ":" + CLASS_NAME + ":list";
    private static final String QUERY_PERM = MODULE_NAME + ":" + CLASS_NAME + ":query";

    @Autowired
    private ClassLoaderService classLoaderService;

    @GetMapping
    @ApiOperation("获取所有类加载器信息")
    @PreAuthorize("hasAnyAuthority('" + LIST_PERM + "')")
    public Result<List<ClassLoaderInfo>> classLoaderInfo() throws IOException {
        List<ClassLoaderInfo> classLoaders = classLoaderService.getClassLoaders();
        return ResultUtil.success(classLoaders);
    }

    @GetMapping("/{CLASS_NAME}")
    @ApiOperation("获取类加载的信息")
    @PreAuthorize("hasAnyAuthority('" + QUERY_PERM + "')")
    public Result<ClassInfo> classInfo(@PathVariable("CLASS_NAME") String name) throws IOException {
        ClassInfo classInfo = name.contains(".") ? classLoaderService.getClassInfo(name) : classLoaderService.getClassInfoByBeanName(name);
        return ResultUtil.success(classInfo);
    }

    @GetMapping("/download/{classPathUrl}")
    @ApiOperation("下载jar资源")
    @PreAuthorize("permitAll()")
    public void download(@PathVariable("classPathUrl") String classPathUrl, HttpServletResponse response) throws IOException {
        classLoaderService.download(classPathUrl, response);
    }

}
