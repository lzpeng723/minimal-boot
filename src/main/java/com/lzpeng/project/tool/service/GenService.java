package com.lzpeng.project.tool.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.lzpeng.project.base.service.FileInfoService;
import com.lzpeng.project.tool.domain.Gen;
import com.lzpeng.project.tool.utils.EntityClassUtil;
import com.lzpeng.project.tool.vo.GenInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * 代码生成模板 业务层
 * @date: 2020-4-16
 * @time: 11:47:29
 * @author: 李志鹏
 */
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class GenService extends AbstractGenService {

    /**
     * 字符串模板引擎(freemarker)
     */
    private static final TemplateEngine STRING_ENGINE = TemplateUtil.createEngine();

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private GenInitService genInitService;


    /**
     * 生成某个实体类的所有代码到用户文件夹
     * @param clazz 实体类
     * @param <T>
     */
    public <T> void genCodeToUserDir(Class<T> clazz) throws IOException {
        List<Gen> genList = genInitService.getInitGenList();
        for (Gen gen : genList) {
            genCodeToUserDir(clazz, gen);
        }
    }

    /**
     * 生成某个实体类的所有代码到压缩文件
     * @param <T>
     * @param clazz 实体类
     * @return
     */
    public <T> File genCodeToZip(Class<T> clazz){
        String tempDir = getTempDir();
        List<Gen> genList = findAll();
        for (Gen gen : genList) {
            genCodeToFile(tempDir, clazz, gen);
        }
        File zipFile = ZipUtil.zip(tempDir);
        FileUtil.del(tempDir);
        return zipFile;
    }

    /**
     * 生成某个实体的某个代码到用户文件夹
     * @param clazz 实体类名称
     * @param id 代码配置id
     * @param <T>
     */
    public <T> void genCodeToUserDir(Class<T> clazz, String id){
        genCodeToUserDir(clazz, id, null);
    }

    /**
     * 生成某个实体的某个代码到用户文件夹
     * @param clazz 实体类名称
     * @param gen 代码配置
     * @param <T>
     */
    public <T> void genCodeToUserDir(Class<T> clazz, Gen gen){
        genCodeToUserDir(clazz, gen, null);
    }

    /**
     * 生成某个实体的某个代码到用户文件夹
     * @param clazz 实体类名称
     * @param id 代码配置id
     * @param override 是否覆盖
     * @param <T>
     */
    public <T> void genCodeToUserDir(Class<T> clazz, String id, Boolean override){
        Gen gen = findById(id);
        genCodeToUserDir(clazz, gen, override);
    }
    /**
     * 生成某个实体的某个代码到用户文件夹
     * @param clazz 实体类名称
     * @param gen 代码配置
     * @param override 是否覆盖
     * @param <T>
     */
    public <T> void genCodeToUserDir(Class<T> clazz, Gen gen, Boolean override){
        Map<String, Object> map = buildEntityTemplateMap(clazz);
        String path = STRING_ENGINE.getTemplate(gen.getPath()).render(map);
        override = override == null ? gen.getOverride() : override;
        // 防止 gen.getOverride() 也为 null
        override = override == null ? false : override;
        genCodeToUserDir(path, gen.getTemplate(), map, override);
    }
    /**
     * 生成某个实体的某个代码到文件
     * @param tempDir 临时文件夹
     * @param clazz 实体类名称
     * @param id 代码配置id
     * @param <T>
     */
    private <T> File genCodeToFile(String tempDir, Class<T> clazz, String id){
        Gen gen = findById(id);
        return genCodeToFile(tempDir, clazz, gen);
    }
    /**
     * 生成某个实体的某个代码到文件
     * @param tempDir 临时文件夹
     * @param clazz 实体类名称
     * @param gen 代码配置
     * @param <T>
     */
    private <T> File genCodeToFile(String tempDir, Class<T> clazz, Gen gen){
        Map<String, Object> map = buildEntityTemplateMap(clazz);
        String path = STRING_ENGINE.getTemplate(gen.getPath()).render(map);
        return genCodeToFile(tempDir, path, gen.getTemplate(), map);
    }

    /**
     * 生成代码到用户文件夹
     * @param path 文件路径
     * @param templateStr 模板字符串
     * @param map 相关变量
     * @param override 是否覆盖生成
     */
    private void genCodeToUserDir(String path, String templateStr, Map<String, Object> map, Boolean override) {
        Path file = Paths.get(USER_DIR, path);
        if (!override && Files.exists(file)) {
            // 当不允许覆盖原文件，且文件存在时，不进行写文件
            log.info("跳过生成 {}", path);
            return;
        }
        try {
            Files.createDirectories(file.getParent());
            Writer out = Files.newBufferedWriter(file);
            Template template = STRING_ENGINE.getTemplate(templateStr);
            template.render(map, out);
            out.flush();
            out.close();
            log.info("已生成 {}", path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成文件
     * @param tempDir 临时文件夹
     * @param path 文件路径
     * @param templateStr 模板字符串
     * @param map 相关变量
     */
    private File genCodeToFile(String tempDir, String path, String templateStr, Map<String, Object> map) {
        Path file = Paths.get(tempDir, path);
        try {
            Files.createDirectories(file.getParent());
            Writer out = Files.newBufferedWriter(file);
            Template template = STRING_ENGINE.getTemplate(templateStr);
            template.render(map, out);
            out.flush();
            out.close();
            log.info("已生成 {}", path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.toFile();
    }

    /**
     * 根据 实体类 构造模板文件中需要使用的 Map
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> Map<String, Object> buildEntityTemplateMap(Class<T> clazz) {
        Map<String, Object> map = new HashMap<>();
        map.put("fullClassName", EntityClassUtil.getFullClassName(clazz));
        map.put("simpleClassName", EntityClassUtil.getSimpleClassName(clazz));
        map.put("superClassName",EntityClassUtil.getSuperSimpleClassName(clazz));
        map.put("entityType",EntityClassUtil.getEntityType(clazz));
        map.put("moduleName", EntityClassUtil.getModuleName(clazz));
        map.put("chineseClassName", EntityClassUtil.getChineseClassName(clazz));
        map.put("editPageType", EntityClassUtil.getEditPageType(clazz));
        ArrayList<Object> columnList = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            Map<String, Object> columnMap = buildFieldTemplateMap(field);
            if (columnMap != null){
                columnList.add(columnMap);
            }
        }
        map.put("columnList", columnList);
        return map;
    }

    /**
     * 构建实体中字段的map
     *
     * @param field
     * @return
     */
    private static Map<String, Object> buildFieldTemplateMap(Field field) {
        if (ClassUtil.isSimpleValueType(field.getType())) {
            ApiModelProperty apiModelProperty = field.getDeclaredAnnotation(ApiModelProperty.class);
            if (apiModelProperty != null && !apiModelProperty.hidden()) {
                Map<String, Object> columnMap = new HashMap<>();
                columnMap.put("fieldType", EntityClassUtil.getFieldType(field));
                columnMap.put("fieldName", EntityClassUtil.getFieldName(field));
                columnMap.put("chineseFieldName", EntityClassUtil.getChineseFieldName(apiModelProperty));
                columnMap.put("apiModelProperty", EntityClassUtil.getApiModelProperty(apiModelProperty));
                return columnMap;
            }
        }
        return Collections.emptyMap();
    }

    /**
     * 查询某实体类代码生成情况
     * @param <T>
     * @param clazz
     * @return
     */
    public <T> List<GenInfo> genningCode(Class<T> clazz) {
        List<Gen> genList = findAll();
        List<GenInfo> genInfoList = new ArrayList<>(genList.size());
        for (Gen gen : genList) {
            Map<String, Object> map = buildEntityTemplateMap(clazz);
            String path = STRING_ENGINE.getTemplate(gen.getPath()).render(map);
            GenInfo genInfo = BeanUtil.copyProperties(gen, GenInfo.class);
            genInfo.setClazz(clazz);
            if (path.startsWith("src/main/java/")) {
                // 去掉 src/main/java 或者 src/test/java
                String classPath = StrUtil.sub(path, "src/main/java/".length(), -".java".length());
                if (ClassUtils.isPresent(classPath.replace("/", "."), null)) {
                    genInfo.setGen("已生成class");
                } else if (Files.exists(Paths.get(USER_DIR, path))) {
                    genInfo.setGen("已生成java");
                } else {
                    genInfo.setGen("未生成java");
                }
            } else if (path.startsWith("src/test/java/")) {
                // 去掉 src/main/java 或者 src/test/java
                String classPath = StrUtil.sub(path, "src/test/java/".length(), -".java".length());
                if (ClassUtils.isPresent(classPath.replace("/", "."), null)) {
                    genInfo.setGen("已生成class");
                } else if (Files.exists(Paths.get(USER_DIR, path))) {
                    genInfo.setGen("已生成测试java");
                } else {
                    genInfo.setGen("测试用例");
                }
            } else {
                genInfo.setGen(FileUtil.extName(path) + "文件");
            }
            genInfoList.add(genInfo);
        }
        return genInfoList;
    }

    /**
     * 下载生成的代码文件
     * @param clazz 实体类
     * @param response 响应
     * @param <T>
     */
    public <T> void downloadCode(Class<T> clazz, HttpServletResponse response) throws IOException {
        File zipFile = genCodeToZip(clazz);
        fileInfoService.downloadFile(zipFile, response);
        FileUtil.del(zipFile);
    }

    /**
     * 下载生成的代码文件
     * @param clazz 实体类
     * @param id 代码模板id
     * @param response 响应
     * @param <T>
     */
    public <T> void downloadCode(Class<T> clazz, String id, HttpServletResponse response) throws IOException {
        String tempDir = getTempDir();
        File file = genCodeToFile(tempDir, clazz, id);
        fileInfoService.downloadFile(file, response);
        FileUtil.del(tempDir);
    }

    /**
     * 预览实体类生成的代码文件
     * @param <T>
     * @param clazz
     * @param id
     * @return
     */
    public <T> Map<String, String> preview(Class<T> clazz, String id) {
        Gen gen = findById(id);
        Map<String, Object> map = buildEntityTemplateMap(clazz);
        String path = STRING_ENGINE.getTemplate(gen.getPath()).render(map);
        String fileType = gen.getType().getMessage();
        String fileName = String.join(".", FileUtil.mainName(path), FileUtil.extName(path)) ;
        String code = STRING_ENGINE.getTemplate(gen.getTemplate()).render(map);
        return MapUtil.builder("code", code)
                .put("fileName", fileName)
                .put("fileType", fileType)
                .build();
    }

}
