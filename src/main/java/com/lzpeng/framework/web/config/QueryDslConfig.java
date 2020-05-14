package com.lzpeng.framework.web.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * QueryDsl 相关Bean
 * @date: 2020/5/11
 * @time: 13:52
 * @author: 李志鹏
 */
@Configuration
public class QueryDslConfig {

    /**
     * QueryDsl 复杂查询使用
     * @param entityManager
     * @return
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
