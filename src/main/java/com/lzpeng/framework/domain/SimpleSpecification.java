package com.lzpeng.framework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * 封装复杂条件查询
 * @date: 2020/4/5
 * @time: 13:42
 * @author:   李志鹏
 * @see {https://www.jianshu.com/p/133610fb5599}
 */
@Data
@AllArgsConstructor(staticName = "create")
public class SimpleSpecification<T> implements Specification<T> {

    /**
     * 条件
     */
    private Collection<SpecificationOperator> operators;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate result = null;
        for (SpecificationOperator operator : operators) {
            if (result == null) {
                result = generatePredicate(root, cb, operator);
            } else {
                Predicate predicate = generatePredicate(root, cb, operator);
                if (predicate == null) {
                    continue;
                }
                switch (operator.getLink()){
                    case AND:
                        result = cb.and(result, predicate);
                        break;
                    case OR:
                        result = cb.or(result, predicate);
                        break;
                    default:
                        break;
                }
            }
        }
        return result;
    }

    /**
     * 根据条件生成where子句
     * @param root
     * @param cb
     * @param op
     * @return
     */
    private Predicate generatePredicate(Root<T> root, CriteriaBuilder cb, SpecificationOperator op) {
        switch (op.getOp()) {
            case GT:
                return cb.gt(root.get(op.getKey()), (Number)op.getValue());
            case GTE:
                return cb.ge(root.get(op.getKey()), (Number)op.getValue());
            case LT:
                return cb.lt(root.get(op.getKey()), (Number)op.getValue());
            case LTE:
                return cb.le(root.get(op.getKey()), (Number)op.getValue());
            case EQUALS:
                return cb.equal(root.get(op.getKey()), op.getValue());
            case NOT_EQUALS:
                return cb.notEqual(root.get(op.getKey()), op.getValue());
            case LIKE:
                return cb.like(root.get(op.getKey()), "%" + op.getValue() + "%");
            case START:
                return cb.like(root.get(op.getKey()), op.getValue() + "%");
            case END:
                return cb.like(root.get(op.getKey()), "%" + op.getValue());
            case IN:
                return cb.in(root.get(op.getKey())).in((Object[]) op.getValue());
            case NULL:
                return cb.isNull(root.get(op.getKey()));
            case NOT_NULL:
                return cb.isNotNull(root.get(op.getKey()));
            default:
                break;
        }
        return null;
    }
}
