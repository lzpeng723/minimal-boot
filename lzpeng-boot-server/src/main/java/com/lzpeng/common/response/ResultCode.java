package com.lzpeng.common.response;

/**
 * Created by mrt on 2018/3/5.
 * 10000-- 通用错误代码
 * 22000-- 媒资错误代码
 * 23000-- 用户中心错误代码
 * 24000-- cms错误代码
 * 25000-- 文件系统
 */
/**
 * 错误代码信息接口
 * @date: 2020/2/1
 * @time: 22:50
 * @author: 李志鹏
 */
public interface ResultCode {
    /**
     * 操作是否成功,true为成功，false操作失败
     * @return
     */
    boolean success();

    /**
     * 操作代码
     * @return
     */
    int code();

    /**
     * 提示信息
     * @return
     */
    String message();

}
