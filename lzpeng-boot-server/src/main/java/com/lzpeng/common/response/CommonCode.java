package com.lzpeng.common.response;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * 公共错误代码
 * @date: 2020/2/1
 * @time: 22:50
 * @author: 李志鹏
 */

@ToString
@AllArgsConstructor
public enum CommonCode implements ResultCode{

    /**
     * 成功返回码
     */
    SUCCESS(true,10200,"操作成功！"),
    /**
     * 失败返回码
     */
    FAIL(false,10000,"操作失败！"),
    /**
     * 参数错误
     */
    ARGUMENT_FAIL(false,10400,"参数错误！"),
    /**
     * 未登录
     */
    UNAUTHORIZED(false,10401,"请登录之后再进行操作！"),
    /**
     * 无权限
     */
    ACCESS_DENIED(false,10403,"权限不足，无权操作！"),
    /**
     * 不允许的HTTP METHOD！
     */
    METHOD_NOT_ALLOWED(false,10405,"不允许的HTTP METHOD！"),
    /**
     * 文件大小超过限制
     */
    FILE_SIZE_LIMIT(false,11000,"文件大小超过限制"),
    /**
     * 服务器内部错误
     */
    SERVER_ERROR(false,10500,"抱歉，系统繁忙，请稍后重试！");
    /**
     * 操作是否成功,true为成功，false操作失败
     */
    boolean success;

    /**
     * 操作代码
     */
    int code;

    /**
     * 提示信息
     */
    String message;

    @Override
    public boolean success() {
        return success;
    }
    @Override
    public int code() {
        return code;
    }
    @Override
    public String message() {
        return message;
    }

    /**
     * 根据 code 获取 CommonCode
     * @param code
     * @return
     */
    public static CommonCode getCommonCode(int code){
        for (CommonCode commonCode : values()) {
            if (commonCode.code == code) {
                return commonCode;
            }
        }
        return FAIL;
    }


}
