package com.example.boost.dao;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Configuration
@EnableTransactionManagement
@MapperScan("com.example.boost.mapper")
public class DatabaseConfig {

    private static String URL;

    private static String USER;

    private static String PASSWORD;

    private static Connection connection = null;
    //单连接
    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
        }
        return connection;
    }
    //连接池
    @Bean
    public DataSource createDatasource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.addDataSourceProperty("connectionTimeout",1000);
        config.addDataSourceProperty("idleTimeout",6000);
        config.addDataSourceProperty("maximumPoolSize",10);
        return new HikariDataSource(config);
    }
    //获取连接
    @Bean
    public Connection getConnections(@Autowired DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }
    //jdbc模板
    @Bean
    public JdbcTemplate createJdbcTemplate(@Autowired DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    //jdbc事务
    @Bean
    public PlatformTransactionManager createPlatformTransactionManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    //mybatis
    @Bean
    public SqlSessionFactory createSqlSessionFactory(@Autowired DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();
    }
    @Value("${spring.datasource.url}")
    public void setURL(String URL) {
        DatabaseConfig.URL = URL;
    }
    @Value("${spring.datasource.username}")
    public void setUSER(String USER) {
        DatabaseConfig.USER = USER;
    }
    @Value("${spring.datasource.password}")
    public void setPASSWORD(String PASSWORD) {
        DatabaseConfig.PASSWORD = PASSWORD;
    }
}
