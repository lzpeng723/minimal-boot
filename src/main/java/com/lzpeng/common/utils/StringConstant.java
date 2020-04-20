package com.lzpeng.common.utils;

/**
 * 字符串常量
 * @date: 2020/2/3
 * @time: 21:31
 * @author: 李志鹏
 */
public class StringConstant {

    /**
     * 两部分编码连接部分
     */
    public static final String DELIMITER = "_";

    /**
     * 两部分编码连接部分
     */
    public static final String PACKAGE_DELIMITER = ".";

    /**
     * 数字
     */
    public static final String NUMBER = "0123456789";

    /**
     * 大写字母
     */
    public static final String UPPERCASE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 小写字母
     */
    public static final String LOWERCASE_ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 大写字母键盘顺序
     */
    public static final String UPPERCASE_KEYBOARD = "QWERTYUIOPASDFGHJKLZXCVBNM";

    /**
     * 小写字母键盘顺序
     */
    public static final String LOWERCASE_KEYBOARD = "qwertyuiopasdfghjklzxcvbnm";

    /**
     * 包名
     */
    public static final String PACKAGE_NAME = LOWERCASE_KEYBOARD + PACKAGE_DELIMITER;

    /**
     * 类全名
     */
    public static final String FULL_CLASS_NAME = String.join("",
            LOWERCASE_KEYBOARD, UPPERCASE_KEYBOARD, PACKAGE_DELIMITER);

    /**
     * 进制转换默认字符
     */
    public static final String DEFAULT_DIGITS = String.join("",
            NUMBER, LOWERCASE_ALPHABET, UPPERCASE_ALPHABET);

    /**
     * 大小写字母键盘顺序
     */
    public static final String KEYBOARD = String.join("",
            LOWERCASE_KEYBOARD, UPPERCASE_KEYBOARD);

    /**
     * 大小写字母字母表顺序
     */
    public static final String ALPHABET = String.join("",
            LOWERCASE_ALPHABET, UPPERCASE_ALPHABET);

    /**
     * 实体类名必须满足的正则表达式
     */
    public static final String ENTITY_NAME_PATTERN = "^com.lzpeng.project.([a-z]+).domain.([A-Z][a-zA-Z]+)$";
    /**
     * 类名模板
     */
    public static final String ENTITY_NAME_FORMAT = "com.lzpeng.project.%s.domain.%s";
}
