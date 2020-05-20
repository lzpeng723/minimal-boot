package com.lzpeng.project.tool.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.lzpeng.project.base.service.FileInfoService;
import com.lzpeng.project.tool.vo.ClassInfo;
import com.lzpeng.project.tool.vo.ClassLoaderInfo;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import sun.misc.Launcher;
import sun.misc.URLClassPath;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @date: 2020/4/16
 * @time: 13:42
 * @author:   李志鹏
 */
@Service
public class ClassLoaderService {


    @Autowired
    private FileInfoService fileInfoService;


    /**
     * 获取类信息
     * @param className 类名
     * @return
     * @throws IOException
     */
    public ClassInfo getClassInfo(String className)  throws IOException{
        if (ClassUtils.isPresent(className, null)) {
            return getClassInfo(ClassLoaderUtil.loadClass(className, false));
        }
        // 没有找到此类
        return ClassInfo.create(null, null, null, getClassLoaders());
    }

    /**
     * 获取类信息
     * @param clazz
     * @return
     * @throws IOException
     */
    public ClassInfo getClassInfo(Class<?> clazz) throws IOException {
        String classPath = clazz.getName().replace('.', '/') + FileUtil.CLASS_EXT;
        ClassLoader classLoader = clazz.getClassLoader();
        if (classLoader != null) {
            ClassLoaderInfo classLoaderInfo = ClassLoaderInfo.create(classLoader, classLoader.getClass().getName(), buildClassPath(classLoader));
            URL url = classLoader.getResource(classPath);
            if (url == null) {
                url = clazz.getProtectionDomain().getCodeSource().getLocation();
                if ("jar".equals(url.getProtocol())) {
                    url = new URL( url + "!/" + classPath);
                } else if ("file".equals(url.getProtocol()) && url.getPath().endsWith(FileUtil.JAR_FILE_EXT)) {
                    url = new URL("jar:" + url + "!/" + classPath);
                } else {
                    url = new URL(url, classPath);
                }
            }
            return ClassInfo.create(clazz, String.valueOf(url), classLoaderInfo, getClassLoaders());
        } else {
            return ClassInfo.create(clazz, null, buildBootstrapClassLoaderInfo(), getClassLoaders());
        }
    }

    /**
     * 获取所有ClassLoader信息
     * @return
     */
    public List<ClassLoaderInfo> getClassLoaders() throws IOException {
        List<ClassLoaderInfo> classLoaderInfos = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        while (classLoader != null) {
            classLoaderInfos.add(ClassLoaderInfo.create(classLoader, classLoader.getClass().getName(), buildClassPath(classLoader)));
            classLoader = classLoader.getParent();
        }
        classLoaderInfos.add(buildBootstrapClassLoaderInfo());
        return classLoaderInfos;
    }

    /**
     * 构建BootstrapClassLoader信息
     * @return
     */
    private ClassLoaderInfo buildBootstrapClassLoaderInfo() {
        URLClassPath bootstrapClassPath = Launcher.getBootstrapClassPath();
        URL[] urls = bootstrapClassPath.getURLs();
        String[] urlsStr = Arrays.stream(urls).map(String::valueOf).toArray(String[]::new);
        return ClassLoaderInfo.create(null, "BootstrapClassLoader", urlsStr);

    }

    /**
     * 获取 classLoader 加载的路径
     * @param classLoader
     * @return
     */
    private String[] buildClassPath(ClassLoader classLoader) throws IOException {
        if (classLoader instanceof URLClassLoader){
            URLClassLoader ucl = (URLClassLoader) classLoader;

            URL[] urls = ucl.getURLs();
            String[] urlsStr = Arrays.stream(urls).map(String::valueOf).toArray(String[]::new);
            return urlsStr;
        } else {
            Enumeration<URL> resources = classLoader.getResources("");
            List<String> urlList = new ArrayList<>();
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                urlList.add(String.valueOf(url));
            }
            return urlList.toArray(new String[urlList.size()]);
        }
    }


    /**
     * 根据URL下载文件
     * @param classPathUrl
     * @param response
     */
    public void download(String classPathUrl, HttpServletResponse response) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(classPathUrl);
        URL url = new URL(new String(bytes));
        String fileName = new File(url.getFile()).getName();
        if (fileName.endsWith("!")) {
            fileName = fileName.substring(0, fileName.length() - 1);
        }
        fileInfoService.downloadFile(fileName, url.openStream(), response);
    }

    /**
     * 通过bean名称搜索ClassInfo
     * @param name
     * @return
     */
    public ClassInfo getClassInfoByBeanName(String name) throws IOException {
        try {
            Object bean = SpringUtil.getBean(name);
            Class<?> clazz = ClassUtils.getUserClass(bean);
            return getClassInfo(clazz);
        } catch (NoSuchBeanDefinitionException e) {
            // 没有找到此Bean
            return ClassInfo.create(null, null, null, getClassLoaders());
        }
    }
}
