package com.lzpeng.project.tool.utils;

import cn.hutool.core.util.TypeUtil;
import com.lzpeng.framework.annotation.GenerateCode;
import com.lzpeng.framework.domain.LeftTreeRightTableEntity;
import com.lzpeng.framework.domain.TreeEntity;
import com.squareup.javapoet.AnnotationSpec;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.lzpeng.common.utils.StringConstant.ENTITY_NAME_PATTERN;

/**
 * @date: 2020/3/12
 * @time: 22:45
 * @author:   李志鹏
 */
public class EntityClassUtil {

    private static final String ENTITY_CLASS_SUFFIX = "Entity"; // 实体基类后缀
    /**
     * 实体类需满足的正则表达式
     */
    private static final Pattern PATTERN = Pattern.compile(ENTITY_NAME_PATTERN);

    /**
     * 获取全类名
     * @param clazz
     * @return
     */
    public static String getFullClassName(Class<?> clazz){
        return clazz.getName();
    }
    /**
     * 获取简单类名
     * @param clazz
     * @return
     */
    public static String getSimpleClassName(Class<?> clazz){
        return clazz.getSimpleName();
    }
    /**
     * 获取简单父类名
     * @param clazz
     * @return
     */
    public static String getSuperSimpleClassName(Class<?> clazz){
        return clazz.getSuperclass().getSimpleName();
    }
    /**
     * 获取实体类型 Base Tree ...
     * @param clazz
     * @return
     */
    public static String getEntityType(Class<?> clazz){
        String superClassName = getSuperSimpleClassName(clazz);
        int endIndex = superClassName.length() - ENTITY_CLASS_SUFFIX.length();
        return superClassName.substring(0, endIndex);
    }

    /**
     * 获取所属模块名
     * @param clazz
     * @return
     */
    public static String getModuleName(Class<?> clazz){
        Matcher matcher = PATTERN.matcher(clazz.getName());
        matcher.matches();
        return matcher.group(1);
    }

    /**
     * 获取中文类名
     * @param clazz
     * @return
     */
    public static String getChineseClassName(Class<?> clazz){
        ApiModel apiModel = clazz.getDeclaredAnnotation(ApiModel.class);
        return apiModel == null ? clazz.getSimpleName() : apiModel.value();
    }

    /**
     * 获取属性类型
     * @param field
     * @return
     */
    public static String getFieldType(Field field){
        return field.getType().getSimpleName();
    }
    /**
     * 获取属性名称
     * @param field
     * @return
     */
    public static String getFieldName(Field field){
        return field.getName();
    }
    /**
     * 获取属性中文名称
     * @param apiModelProperty
     * @return
     */
    public static String getChineseFieldName(ApiModelProperty apiModelProperty){
        return apiModelProperty.value();
    }
    /**
     * 获取swagger property信息
     * @param apiModelProperty
     * @return
     */
    public static String getApiModelProperty(ApiModelProperty apiModelProperty){
        AnnotationSpec apiModelPropertySpec = AnnotationSpec.get(apiModelProperty);
        return apiModelPropertySpec.toString().replace(ApiModelProperty.class.getPackage().getName()+".", "");
    }


    /**
     * 获取是详情页面还是弹出框
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> String getEditPageType(Class<T> clazz) {
        GenerateCode generateCode = clazz.getAnnotation(GenerateCode.class);
        GenerateCode.PageType editPage = generateCode != null ? generateCode.editPage() : (GenerateCode.PageType) AnnotationUtils.getDefaultValue(GenerateCode.class, "editPage");
        return editPage.name().toLowerCase();
    }

    /**
     * 如果是左树右表实体则返回左树类型,否则返回空
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Class<? extends TreeEntity> getLeftTreeType(Class<T> clazz) {
        if (LeftTreeRightTableEntity.class.isAssignableFrom(clazz)) {
            Type type = TypeUtil.getTypeArgument(clazz);
            if (type != null && type instanceof Class) {
                return (Class) type;
            }
        }
        return null;
    }
}
