package com.lzpeng.project.base;

import cn.hutool.core.exceptions.ExceptionUtil;
import org.junit.Test;

import java.util.Arrays;

/**
 * @date: 2020/2/22
 * @time: 21:15
 * @author: 李志鹏
 */
public class ExceptionTest {

    @Test
    public void testException(){
        try {
            RuntimeException e = new RuntimeException("运行期异常1");
            throw e;
        } catch (Exception wap) {
            Throwable e = ExceptionUtil.unwrap(wap);
            System.out.println(e.getCause());
            System.out.println("__________");
            System.out.println(e.getMessage());
            System.out.println("__________");
            System.out.println(e.getLocalizedMessage());
            System.out.println("__________");
            System.out.println();
            System.out.println("__________");
            System.out.println(Arrays.toString(e.getSuppressed()));
        }
    }
}
