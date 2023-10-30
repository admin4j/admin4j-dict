package com.admin4j.dict.provider.sql.autoconfigure;

import com.admin4j.dict.provider.sql.SqlDictManager;
import com.admin4j.dict.provider.sql.SqlDictProvider;
import com.admin4j.dict.provider.sql.SqlDictService;
import com.admin4j.dict.provider.sql.impl.JdbcSqlDictManager;
import com.admin4j.dict.provider.sql.impl.MybatisSqlDictManager;
import com.admin4j.dict.provider.sql.impl.mapper.SqlDictMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author andanyang
 * @since 2022/10/25 16:55
 */
// @MapperScans(@MapperScan("com.admin4j.dict.provider.sql.impl.mapper"))
// @ComponentScan("com.admin4j.dict.provider.sql.impl.mapper")
// @MapperScan("com.admin4j.dict.provider.sql.impl.mapper")
@AutoConfigureOrder(value = Integer.MAX_VALUE)
public class SqlProviderAutoconfigure {


    @Bean
    @ConditionalOnClass(name = "org.mybatis.spring.annotation.MapperScan")
    public MapperFactoryBean<SqlDictMapper> sqlDictMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<SqlDictMapper> sqlDictMapperMapperFactoryBean = new MapperFactoryBean<>(SqlDictMapper.class);
        sqlDictMapperMapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        return sqlDictMapperMapperFactoryBean;
    }

    @Bean
    @ConditionalOnClass(name = "org.mybatis.spring.annotation.MapperScan")
    @ConditionalOnBean(SqlDictMapper.class)
    public MybatisSqlDictManager mybatisSqlDictManager(SqlDictMapper sqlDictMapper) {

        return new MybatisSqlDictManager(sqlDictMapper);
    }


    @Bean
    @ConditionalOnBean(SqlDictManager.class)
    public SqlDictProvider sqlDictProvider(SqlDictManager sqlDictManager, @Autowired(required = false) SqlDictService sqlDictService) {
        return new SqlDictProvider(sqlDictManager, sqlDictService);
    }


    @Bean
    @ConditionalOnClass(name = "org.springframework.jdbc.core.JdbcTemplate")
    @ConditionalOnBean(name = {"jdbcTemplate"})
    @ConditionalOnMissingBean(MybatisSqlDictManager.class)
    public JdbcSqlDictManager jdbcSqlDictManager(JdbcTemplate jdbcTemplate) {
        return new JdbcSqlDictManager(jdbcTemplate);
    }


}
