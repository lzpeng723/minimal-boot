package com.lzpeng.common.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

import java.math.BigInteger;

/**
 * 进制转换工具
 * @date: 2020/2/2
 * @time: 21:39
 * @author: 李志鹏
 */
public class RadixNumberUtil {

    @Deprecated
    private static final int LOG64 = 6; // log2(64) = 6

    private static final String DEFAULT_DIGITS = StringConstant.DEFAULT_DIGITS;

    /**
     * 转为64进制
     * 其他 36 以下进制 jdk 原生有 不必要重写
     *
     * @param number
     * @return
     * @see {@link #toString(int, int, String)} and {@link #toString(long, int, String)}
     */
    @Deprecated
    public static String toRadix64(long number) {

        if (number == 0) {
            return "0";
        }
        if (number < 0) {
            return "-" + toRadix64(-number);
        }
        char[] digits = DEFAULT_DIGITS.toCharArray();
        String binStr = Long.toBinaryString(number);
        int offset = binStr.length() % LOG64; // 除以6 的余数  因为 2^6 = 64
        if (offset != 0) {
            // 前补 0
            offset = LOG64 - offset;
            StringBuilder prefixBuilder = new StringBuilder();
            for (int i = 0; i < offset; i++) {
                prefixBuilder.append('0');
            }
            binStr = prefixBuilder.append(binStr).toString();
        }
        char[] charArray = binStr.toCharArray();
        StringBuilder destBuilder = new StringBuilder();
        for (int i = 0; i < charArray.length; i += LOG64) {
            int base64 = 0;
            for (int j = 0; j < LOG64; j++) {
                int binNum = charArray[i + j] - 48; // 取当前字符转为 int 的 0或1
                base64 += binNum << (LOG64 - j - 1); // 左移 log64 - j 位
            }
            destBuilder.append(digits[base64]);
        }
        return destBuilder.toString();

    }

    /**
     * 转为任意进制，不受jdk最大36的限制
     *
     * @param i
     * @param radix
     * @return
     */
    public static String toString(long i, int radix, String digits) {
        Assert.isFalse(StrUtil.length(digits) == 1, "digits 的字符数量不能为 1");
        digits = StrUtil.isBlank(digits) ? DEFAULT_DIGITS : digits;
        Assert.isFalse(radix < Character.MIN_RADIX || radix > digits.length(), "错误的进制 {}, 支持的进制范围为 {} 至 {}", radix, Character.MIN_RADIX, digits.length());
        if (radix == 10) {
            return String.valueOf(i);
        }
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);

        if (!negative) {
            i = -i;
        }

        while (i <= -radix) {
            buf[charPos--] = digits.charAt((int) (-(i % radix)));
            i = i / radix;
        }
        buf[charPos] = digits.charAt((int) (-i));

        if (negative) {
            buf[--charPos] = '-';
        }

        return new String(buf, charPos, (65 - charPos));
    }

    /**
     * 转为任意进制，不受jdk最大36的限制
     *
     * @param i
     * @param radix
     * @return
     */
    public static String toString(int i, int radix, String digits) {
        Assert.isFalse(StrUtil.length(digits) == 1, "digits 的字符数量不能为 1");
        digits = StrUtil.isBlank(digits) ? DEFAULT_DIGITS : digits;
        Assert.isFalse(radix < Character.MIN_RADIX || radix > digits.length(), "错误的进制 {}, 支持的进制范围为 {} 至 {}", radix, Character.MIN_RADIX, digits.length());
        if (radix == 10) {
            return String.valueOf(i);
        }
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);
        if (!negative) {
            i = -i;
        }
        while (i <= -radix) {
            buf[charPos--] = digits.charAt(-(i % radix));
            i = i / radix;
        }
        buf[charPos] = digits.charAt(-i);
        if (negative) {
            buf[--charPos] = '-';
        }
        return new String(buf, charPos, (65 - charPos));
    }

    /**
     * 转为任意进制，不受jdk最大36的限制
     * @param num
     * @param radix
     * @param digits
     * @return
     */
    public static String toString(BigInteger num, int radix, String digits){
        Assert.isFalse(StrUtil.length(digits) == 1, "digits 的字符数量不能为 1");
        digits = StrUtil.isBlank(digits) ? DEFAULT_DIGITS : digits;
        Assert.isFalse(radix < Character.MIN_RADIX || radix > digits.length(), "错误的进制 {}, 支持的进制范围为 {} 至 {}", radix, Character.MIN_RADIX, digits.length());
        return BigIntegerUtil.toString(num, radix, digits);
    }

    /**
     * 任意进制转为10进制
     *
     * @param str
     * @param radix
     * @return
     * @throws NumberFormatException
     */
    public static int parseInt(String str, int radix, String digits) {
        Assert.isFalse(StrUtil.length(digits) == 1, "digits 的字符数量不能为 1");
        digits = StrUtil.isBlank(digits) ? DEFAULT_DIGITS : digits;
        Assert.isFalse(radix < Character.MIN_RADIX || radix > digits.length(), "错误的进制 {}, 支持的进制范围为 {} 至 {}", radix, Character.MIN_RADIX, digits.length());
        Assert.notBlank(str, "传入的数字为空");
        int result = 0;
        boolean negative = false;
        int i = 0, len = str.length();
        int limit = -Integer.MAX_VALUE;
        char firstChar = str.charAt(0);
        if (digits.indexOf(firstChar) < 0) { // Possible leading "+" or "-"
            if (firstChar == '-') {
                negative = true;
                limit = Integer.MIN_VALUE;
            } else {
                Assert.isFalse(firstChar == '+', "\"{}\" 中的 {} 是不合法字符", str, firstChar);
            }
            Assert.isFalse(len == 1, "\"{}\" 是不合法字符串,不能仅包含 '+' 或者 '-'", str);
            i++;
        }
        int multmin = limit / radix;
        while (i < len) {
            // Accumulating negatively avoids surprises near MAX_VALUE
            char ch = str.charAt(i++);
            int digit = digits.indexOf(ch);
            Assert.isTrue(digit >= 0 && digit < radix, "\"{}\" 中的 {} 是不合法字符", str, ch);
            Assert.isTrue(result > multmin, "{} 进制数字 \"{}\" 已超过 Integer 可存储的最大数字 {}", radix, str, Integer.MAX_VALUE);
            result *= radix;
            Assert.isTrue(result > limit + digit, "{} 进制数字 \"{}\" 已超过 Integer 可存储的最大数字 {}", radix, str, Integer.MAX_VALUE);
            result -= digit;
        }
        return negative ? result : -result;
    }

    /**
     * 任意进制转为10进制
     *
     * @param str
     * @param radix
     * @return
     * @throws NumberFormatException
     */
    public static long parseLong(String str, int radix, String digits) {
        Assert.isFalse(StrUtil.length(digits) == 1, "digits 的字符数量不能为 1");
        digits = StrUtil.isBlank(digits) ? DEFAULT_DIGITS : digits;
        Assert.isFalse(radix < Character.MIN_RADIX || radix > digits.length(), "错误的进制 {}, 支持的进制范围为 {} 至 {}", radix, Character.MIN_RADIX, digits.length());
        Assert.notBlank(str, "传入的数字为空");
        long result = 0;
        boolean negative = false;
        int i = 0, len = str.length();
        long limit = -Long.MAX_VALUE;
        char firstChar = str.charAt(0);
        if (digits.indexOf(firstChar) < 0) { // Possible leading "+" or "-"
            if (firstChar == '-') {
                negative = true;
                limit = Long.MIN_VALUE;
            } else {
                Assert.isFalse(firstChar == '+', "\"{}\" 中的 {} 是不合法字符", str, firstChar);
            }
            Assert.isFalse(len == 1, "\"{}\" 是不合法字符串,不能仅包含 '+' 或者 '-'", str);
            i++;
        }
        long multmin = limit / radix;
        while (i < len) {
            // Accumulating negatively avoids surprises near MAX_VALUE
            char ch = str.charAt(i++);
            int digit = digits.indexOf(ch);
            Assert.isTrue(digit >= 0 && digit < radix, "\"{}\" 中的 {} 是不合法字符", str, ch);
            Assert.isTrue(result > multmin, "{} 进制数字 \"{}\" 已超过 Long 可存储的最大数字 {}", radix, str, Long.MAX_VALUE);
            result *= radix;
            Assert.isTrue(result > limit + digit, "{} 进制数字 \"{}\" 已超过 Long 可存储的最大数字 {}", radix, str, Long.MAX_VALUE);
            result -= digit;

        }
        return negative ? result : -result;
    }

    /**
     * 任意进制转为 10 进制
     * @return
     * @param str
     * @param radix
     * @param digits
     */
    public static BigInteger parseBigInteger(String str, int radix, String digits){
        Assert.isFalse(StrUtil.length(digits) == 1, "digits 的字符数量不能为 1");
        digits = StrUtil.isBlank(digits) ? DEFAULT_DIGITS : digits;
        Assert.isFalse(radix < Character.MIN_RADIX || radix > digits.length(), "错误的进制 {}, 支持的进制范围为 {} 至 {}", radix, Character.MIN_RADIX, digits.length());
        return BigIntegerUtil.valueOf(str, radix, digits);
    }

//    public static void report(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
////        long id = 230346818;35800528176353154609563 b5ZfMRyGDiaQX
//        BigInteger id = new BigInteger("-35800528176355903008059");
//        BigInteger id2 = new BigInteger("35800528176353154609563");
////        BigInteger id = new BigInteger("230346818");
////        BigInteger id = BigInteger.valueOf(Long.MAX_VALUE/22);
//        String digits = DEFAULT_DIGITS;
//        int radix = DEFAULT_DIGITS.length();
//        System.out.println(id);
//        System.out.println(toString(id, radix, digits));
////        System.out.println(parseLong(toString(id, radix, digits), radix, digits));
//        System.out.println(parseBigInteger(toString(id, radix, digits), radix, digits));
//        System.out.println("____jdk________");
////        System.out.println(id.toString(radix));
////        System.out.println(new BigInteger("a9g5tvhgxvyi4bk", radix));
//    }


}
