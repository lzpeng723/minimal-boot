package com.lzpeng.framework.annotation;


import java.lang.annotation.*;

/**
 * @date: 2020/3/13
 * @time: 23:04
 * @author:   李志鹏
 * 控制实体代码 是否生成
 * */
@Documented
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GenerateCode {

    /**
     * 码 生成路径 若为null 则不生成
     * 配置为 false 后 将不考虑其他配置， 不生成任何代码
     * @return
     */
    boolean generate() default true;

    /**
     * 编辑页面样式 默认 详情页面 可选Dialog页面
     * @return
     */
    PageType editPage() default PageType.DETAIL;

    enum PageType {
        /**
         * 详情页面打开
         */
        DETAIL,
        /**
         * 弹出框方式打开
         */
        DIALOG;
    }


}
