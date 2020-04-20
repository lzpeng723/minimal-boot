package com.lzpeng.framework.domain;

/**
 * @date: 2020/3/18
 * @time: 0:19
 * @author:   李志鹏
 */
public interface BaseEnum<DB>{

    /**
     * 得到存在程序中传输的值
     * @return
     */
    DB getCode();

    /**
     * 得到前端显示的值
     * @return
     */
    String getMessage();
}

