//package com.lzpeng.sys.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.annotation.Resource;
//import javax.persistence.EntityManager;
//import javax.sql.DataSource;
//import java.util.Map;
//
//@Configuration
//@EnableTransactionManagement
////entityManagerFactoryRef:指定实体管理器工厂,transactionManagerRef:指定事务管理器
////basePackages:指定该数据源的repository所在包路径
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "sysEntityManagerFactory", // 实体管理引用
//        transactionManagerRef = "sysTransactionManager", // 事务管理引用
//        basePackages = {"com.lzpeng.sys.repository"}
//)
//@ConditionalOnProperty(prefix = "spring.datasource.sys", name = "url")
//public class SysDataSourceConfig {
//
//    @Autowired
//    @Qualifier("sysDataSource")
//    private DataSource sysDataSource;
//    @Autowired
//    @Qualifier("vendorProperties")
//    private Map<String, Object> vendorProperties;
//
//    /**
//     * 配置第二数据源
//     * @return 数据源
//     */
//    @Bean(name = "sysDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.sys")
//    public static DataSource sysDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    /**
//     * 配置第二数据源实体管理工厂的bean
//     * @param builder EntityManagerFactoryBuilder
//     * @return LocalContainerEntityManagerFactoryBean
//     */
//    @Bean(name = "sysEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean sysEntityManagerFactory(EntityManagerFactoryBuilder builder) {
//        return builder.dataSource(sysDataSource)
//                //指定组合jpaProperties和hibernateProperties配置的map对象
//                .properties(vendorProperties)
//                //指定该数据源的实体类所在包路径
//                .packages("com.lzpeng.domain.sys")
//                .persistenceUnit("sysPersistenceUnit")
//                .build();
//    }
//
//    /**
//     * 配置第二数据源实体管理器
//     * @param builder EntityManagerFactoryBuilder
//     * @return EntityManager
//     */
//    @Bean(name = "sysEntityManager")
//    public EntityManager entityManagerSecondary(EntityManagerFactoryBuilder builder) {
//        return sysEntityManagerFactory(builder).getObject().createEntityManager();
//    }
//
//    /**
//     * 配置第二数据源事务管理器
//     * @param builder EntityManagerFactoryBuilder
//     * @return PlatformTransactionManager
//     */
//    @Bean(name = "sysTransactionManager")
//    public PlatformTransactionManager sysTransactionManager(EntityManagerFactoryBuilder builder) {
//        return new JpaTransactionManager(sysEntityManagerFactory(builder).getObject());
//    }
//}
//
