package com.lzpeng.framework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 封装的Specification条件
 * @date: 2020/4/5
 * @time: 13:49
 * @author:   李志鹏
 */
@Data
@AllArgsConstructor
public class SpecificationOperator {
    private String key;
    private Op op;
    private Object value;
    private Link link;

    @Getter
    @AllArgsConstructor
    public enum Op implements BaseEnum<String> {
        /**
         * 等于
         */
        EQUALS("=", "等于"),
        /**
         * 不等于
         */
        NOT_EQUALS("!=", "不等于"),
        /**
         * 类似
         */
        LIKE("like", "类似"),
        /**
         * 开始以
         */
        START("start", "开始以"),
        /**
         * 结束以
         */
        END("end", "结束以"),
        /**
         * 为空
         */
        NULL("null", "为空"),
        /**
         * 不为空
         */
        NOT_NULL("notnull", "不为空"),
        /**
         * 大于
         */
        GT(">", "大于"),
        /**
         * 大于等于
         */
        GTE(">=", "大于等于"),
        /**
         * 小于
         */
        LT("<", "小于"),
        /**
         * 小于等于
         */
        LTE("<=", "小于等于"),
        /**
         * 在列表
         */
        IN("in", "在列表"),
        ;
        private String code;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    public enum Link implements BaseEnum<String>{
        /**
         * 并且
         */
        AND("and", "并且"),
        /**
         * 或者
         */
        OR("or", "或者"),
        ;
        private String code;
        private String message;
    }

}
