package com.lzpeng.framework.domain;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 复杂查询构建器
 * @date: 2020/4/5
 * @time: 14:30
 * @author:   李志鹏
 */
public class SimpleSpecificationBuilder {

    /**
     * 查询条件
     */
    private Collection<SpecificationOperator> operators;

    public SimpleSpecification build(){
        return SimpleSpecification.create(operators);
    }

    public SimpleSpecificationBuilder(String key, SpecificationOperator.Op op, Object value){
        operators = new ArrayList<>();
        operators.add(new SpecificationOperator(key, op, value, SpecificationOperator.Link.AND));
    }

    public SimpleSpecificationBuilder and(String key, SpecificationOperator.Op op, Object value){
        operators.add(new SpecificationOperator(key, op, value, SpecificationOperator.Link.AND));
        return this;
    }

    public SimpleSpecificationBuilder or(String key, SpecificationOperator.Op op, Object value){
        operators.add(new SpecificationOperator(key, op, value, SpecificationOperator.Link.OR));
        return this;
    }
}
