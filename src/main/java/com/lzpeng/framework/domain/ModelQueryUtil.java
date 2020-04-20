package com.lzpeng.framework.domain;

import cn.hutool.core.util.ReflectUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @date: 2020/2/1
 * @time: 23:58
 * @author: 李志鹏
 * TODO 封装按时间段查询等复杂查询 支持自定义 and or < > 等条件的
 */
public class ModelQueryUtil {

    /**
     * jpa 动态条件查询,类似 mybatis 标签
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> Specification getSpecification(T entity) {
        return new Specification<T>() {
            /**
             * @param root 跟对象，也就是要把条件封装到哪个对象中 WHERE 类名 = entity.getId
             * @param query 封装的是查询关键字 GROUP BY, ORDER BY 等
             * @param cb 封装查询对象，如果直接返回null，表示不需要条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                try {
                    for (Field field : ReflectUtil.getFields(entity.getClass())) {
                        field.setAccessible(true);
                        Object obj = field.get(entity);
                        if (String.class.isAssignableFrom(field.getType())) {
                            if (!StringUtils.isEmpty(obj)) {
                                Predicate predicate = cb.like(root.get(field.getName()).as(String.class), "%" + obj + "%");
                                predicateList.add(predicate);
                            }
                        } else {
                            if (Objects.nonNull(obj)) {
                                Predicate predicate = cb.equal(root.get(field.getName()).as(field.getType()), obj);
                                predicateList.add(predicate);
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                return cb.and(predicateList.toArray(predicates));
            }
        };
    }
}
