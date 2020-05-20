package com.lzpeng.common.utils;

import cn.hutool.core.util.StrUtil;

import java.math.BigInteger;

/**
 * BigInteger 进制转换工具
 * @date: 2020/2/3
 * @time: 21:56
 * @author: 李志鹏
 */
public class BigIntegerUtil {
    /**
     *  * 初始化 62 进制数据，索引位置代表字符的数值，比如 A代表10，z代表61等
     *  
     */
    private static final String DEFAULT_DIGITS = StringConstant.DEFAULT_DIGITS;


    /**
     * 10 进制 转 任意进制
     * @param num
     * @param radix
     * @param digits
     * @return
     */
    static String toString(BigInteger num, int radix, String digits) {
        return toString(num, BigInteger.valueOf(radix), digits);
    }
    /**
     * 10 进制 转 任意进制
     * @param num
     * @param radix
     * @param digits
     * @return
     */
    private static String toString(BigInteger num, BigInteger radix, String digits) {
        if (radix.compareTo(BigInteger.valueOf(10)) == 0) {
            return String.valueOf(num);
        }
        boolean negative = (num.signum() < 0);
        if (negative) {
            // 如果是负数, 取其正数部分
            num = num.negate();
        }
        StringBuilder builder = new StringBuilder();
        while (num.compareTo(radix) > 0) {
            builder.append(digits.charAt(num.mod(radix).intValue()));
            num = num.divide(radix);
        }
        builder.append(digits.charAt(num.intValue()));
        if (negative) {
            builder.append('-');
        }
        return builder.reverse().toString();
    }

    /**
     * 任意进制转10进制
     * @param str
     * @param radix
     * @param digits
     * @return
     */
    static BigInteger valueOf(String str, int radix, String digits) {
        return valueOf(str, BigInteger.valueOf(radix), digits);
    }
    /**
     * 任意进制转10进制
     * @param str
     * @param radix
     * @param digits
     * @return
     */
    private static BigInteger valueOf(String str, BigInteger radix, String digits) {
        /**
          * 将 0 开头的字符串进行替换
          */
        digits = StrUtil.isBlank(digits) ? DEFAULT_DIGITS : digits;
        str = str.replace("^0*", "");
        boolean negative = false;
        char firstChar = str.charAt(0);
        if (firstChar == '-'){
            negative = true;
            str = str.substring(1);
        } else if (firstChar == '+'){
            str = str.substring(1);
        }
        BigInteger num = BigInteger.ZERO;
        for (int i = 0; i < str.length(); i++) {
            int index = digits.indexOf(str.charAt(i));
            BigInteger bigIndex = BigInteger.valueOf(index);
            BigInteger temp = radix.pow(str.length() - i - 1);
            num = num.add(bigIndex.multiply(temp));
        }
        return negative ? num.negate() : num;
    }

}
