package com.lzpeng.common.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.lzpeng.common.annotation.Excel;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date: 2020/4/17
 * @time: 12:58
 * @author:   李志鹏
 */
public class ExcelUtils {

    /**
     * 将集合写入文件
     * @param dataList 数据
     * @param file 文件
     * @param <T>
     */
    public static <T> File writeDataToFile(List<T> dataList, File file) throws IOException {
        Files.createDirectories(file.toPath().getParent());
        OutputStream outputStream = Files.newOutputStream(file.toPath());
        writeDataToStream(dataList, outputStream);
        return file;
    }

    /**
     * 将集合写入流
     * @param dataList 数据
     * @param outputStream 输出流
     * @param <T>
     */
    public static <T> void writeDataToStream(List<T> dataList, OutputStream outputStream) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        Class<T> clazz = (Class<T>) dataList.get(0).getClass();
        for (T data : dataList) {
            Map<String, Object> map = new HashMap<>();
            for (Field field : ReflectUtil.getFields(clazz)) {
                Excel excel = field.getAnnotation(Excel.class);
                if (excel != null && excel.exported()) {
                    map.put(excel.name(), ReflectUtil.getFieldValue(data, field));
                }
            }
            list.add(map);
        }
        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //一次性写出内容，强制输出标题
        writer.write(list, true);
        // 写入到流
        writer.flush(outputStream, true);
        //关闭writer，释放内存
        writer.close();
    }

    /**
     * 从输入流读取数据集合
     * @param inputStream 输入流
     * @param clazz Bean类型
     * @param <T>
     */
    public static <T> List<T> readDataFromStream(InputStream inputStream, Class<T> clazz) {
        List<T> dataList = new ArrayList<>();
        List<Map<String, Object>> list = ExcelUtil.getReader(inputStream).readAll();
        IoUtil.close(inputStream);
        if (CollectionUtils.isEmpty(list)) {
            return dataList;
        }
        // 字段名 和 Excel列名 Map  name:名称
        Map<String, String> keyNameMap = new HashMap<>();
        for (Field field : ReflectUtil.getFields(clazz)) {
            Excel excel = field.getAnnotation(Excel.class);
            if (excel != null && excel.imported()) {
                keyNameMap.put(field.getName(), excel.name());
            }
        }
        for (Map<String, Object> map : list) {
            T data = ReflectUtil.newInstance(clazz);
            // 遍历 字段名 和 Excel列名 Map
            for (Map.Entry<String, String> entry : keyNameMap.entrySet()) {
                Object fieldValue = map.get(entry.getValue());
                if (!StringUtils.isEmpty(fieldValue)) {
                    ReflectUtil.setFieldValue(data, entry.getKey(), fieldValue);
                }
            }
            dataList.add(data);
        }
        return dataList;
    }

    /**
     * 从文件读取数据集合
     * @param file 文件
     * @param clazz Bean类型
     * @param <T>
     */
    public static <T> List<T> readDataFromFile(File file, Class<T> clazz) throws IOException {
        return readDataFromStream(Files.newInputStream(file.toPath()), clazz);
    }
}
