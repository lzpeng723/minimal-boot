package com.lzpeng.common.utils;

import com.lzpeng.project.sys.domain.User;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 进制编码解码工具
 * @date: 2020/2/3
 * @time: 23:10
 * @author: 李志鹏
 */
public class Radix {

    /**
     * 类名可能出现的所有字符
     */
    private static final String SIMPLE_CLASS_NAME = StringConstant.KEYBOARD;

    /**
     * 包名可能出现的所有字符
     */
    private static final String PACKAGE_NAME = StringConstant.PACKAGE_NAME;

    /**
     * 编码后的字符
     */
    private static final String DEFAULT_DIGITS = StringConstant.DEFAULT_DIGITS;
    /**
     * 包名与类名分隔字符
     */
    private static final String DELIMITER = StringConstant.DELIMITER;
    /**
     * 实体类必须符合的正则表达式
     */
    private static final String PATTERN = StringConstant.ENTITY_NAME_PATTERN;
    /**
     * 类名模板
     */
    private static final String FORMAT = StringConstant.ENTITY_NAME_FORMAT;

    /**
     * 对数字编码
     * @param number
     * @return
     */
    private static String encodeNumber(int number){
        return RadixNumberUtil.toString(number, DEFAULT_DIGITS.length(), DEFAULT_DIGITS);
    }
    /**
     * 对数字编码
     * @param number
     * @return
     */
    private static String encodeNumber(long number){
        return RadixNumberUtil.toString(number, DEFAULT_DIGITS.length(), DEFAULT_DIGITS);
    }
    /**
     * 对数字编码
     * @param number
     * @return
     */
    private static String encodeNumber(BigInteger number){
        return RadixNumberUtil.toString(number, DEFAULT_DIGITS.length(), DEFAULT_DIGITS);
    }

    /**
     * 对数字编码
     * @param number
     * @return
     */
    private static String encodeNumber(Number number){
        if (number instanceof BigInteger) {
            return encodeNumber((BigInteger)number);
        }
        return encodeNumber(number.longValue());
    }


    /**
     * 编码实体类
     * @param clazz 实体类 class
     * @return
     */
    private static String encodeEntityClazz(Class<?> clazz){
        return encodeEntityClazz(clazz.getName());
    }

    /**
     * 编码实体类
     * @param clazzName 实体类全名
     * @return
     */
    private static String encodeEntityClazz(String clazzName){
        Matcher matcher = Pattern.compile(PATTERN).matcher(clazzName);
        if (matcher.matches()) {
            String packageName = matcher.group(1); // 包名
            String simpleClazzName = matcher.group(2); // 类名
            return String.join(DELIMITER, encodePackageName(packageName), encodeClazzName(simpleClazzName));
        } else {
            throw new IllegalArgumentException("实体类名不合法: " + clazzName + ",必须匹配正则表达式: " + PATTERN);
        }
    }
    /**
     * 编码类名
     * @param clazzName
     * @return
     */
    private static String encodeClazzName(String clazzName) {
        BigInteger num = RadixNumberUtil.parseBigInteger(clazzName, SIMPLE_CLASS_NAME.length(), SIMPLE_CLASS_NAME);
        return RadixNumberUtil.toString(num, DEFAULT_DIGITS.length(), DEFAULT_DIGITS);
    }

    /**
     * 编码包名
     * @param packageName
     * @return
     */
    private static String encodePackageName(String packageName) {
        BigInteger num = RadixNumberUtil.parseBigInteger(packageName, PACKAGE_NAME.length(), PACKAGE_NAME);
        return RadixNumberUtil.toString(num, DEFAULT_DIGITS.length(), DEFAULT_DIGITS);
    }


    /**
     * 解码类名
     * @param encodeClazzName
     * @return
     */
    private static String decodeEntityClazz(String encodeClazzName){
        String[] encodes = encodeClazzName.split("\\" + DELIMITER);
        String packageName = decodePackageName(encodes[0]);
        String clazzName = decodeClazzName(encodes[1]);
        return String.format(FORMAT, packageName, clazzName);
    }

    /**
     * 解码类名
     * @param encodeClazzName 简单类名
     * @return
     */
    private static String decodeClazzName(String encodeClazzName) {
        BigInteger num = RadixNumberUtil.parseBigInteger(encodeClazzName, DEFAULT_DIGITS.length(), DEFAULT_DIGITS);
        return RadixNumberUtil.toString(num, SIMPLE_CLASS_NAME.length(), SIMPLE_CLASS_NAME);
    }

    /**
     * 解码包名
     * @param encodePackageName 包名
     * @return
     */
    private static String decodePackageName(String encodePackageName) {
        BigInteger num = RadixNumberUtil.parseBigInteger(encodePackageName, DEFAULT_DIGITS.length(), DEFAULT_DIGITS);
        return RadixNumberUtil.toString(num, PACKAGE_NAME.length(), PACKAGE_NAME);
    }

    /**
     * 对数字解码
     * @param str
     * @return
     */
    private static Number decodeNumber(String str){
        return RadixNumberUtil.parseBigInteger(str, DEFAULT_DIGITS.length(), DEFAULT_DIGITS);
    }

    /**
     * 编码实体Id
     * @param clazz 实体类
     * @param id snowflake雪花id
     * @param <T>
     * @return
     */
    public static <T> String encodeEntityId(Class<T> clazz,Number id){
        String encodeClazzName = encodeEntityClazz(clazz);
        String encodeId = encodeNumber(id);
        return String.join(DELIMITER, encodeClazzName, encodeId);
    }

    /**
     * 解码实体id
     * @param encodeId 编码后的实体id
     * @param <T>
     * @return
     */
    public static <T> String decodeEntityId(String encodeId){
        String[] encodes = encodeId.split("\\" + DELIMITER);
        String packageName = decodePackageName(encodes[0]);
        String clazzName = decodeClazzName(encodes[1]);
        Number longId = decodeNumber(encodes[2]);
        return String.format(FORMAT, packageName, clazzName) + ":" + longId;
    }
    public static void main(String[] args) {
        System.out.println(encodeEntityClazz(User.class));
        System.out.println(decodeEntityClazz("27H_j0hl"));
    }
}
