package com.neo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.neo.mapper.primary", sqlSessionFactoryRef = "primarySqlSessionFactory")
public class PrimaryDataSourceConfig {
    @Primary
    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(com.zaxxer.hikari.HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "primarySqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("primaryDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        /** 设置mybatis configuration 扫描路径 */
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("/mybatis/mybatis-config.xml"));
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/primary/*.xml"));
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactory;
    }


    @Primary
    @Bean(name = "primarySqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean(name = "primaryTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
