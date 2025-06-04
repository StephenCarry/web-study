package com.example.boost.dao;

import com.example.boost.entry.UserLogin;
import com.example.boost.mapper.UserLoginMapper;
import com.example.boost.config.DatabaseConfig;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.managed.ManagedTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

@Component
public class UserLoginDao {
    @Autowired
    Connection connection;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Qualifier("createPlatformTransactionManager")
    @Autowired
    PlatformTransactionManager txManager;
    @Autowired
    UserLoginMapper userLoginMapper;
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public List<UserLogin> queryList() {
        return userLoginMapper.query();
    }

    public Map<String,Object> queryById(int id) {
        //jdbc
//        Map<String,Object> map = new HashMap<>();
//        try {
//            Connection connection = this.connection;
//            //Statement容易被sql注入，PreparedStatement则不会
////            try(Statement statement = connection.createStatement()) {
//            try(PreparedStatement statement = connection.prepareStatement("select * from user_login " +
//                    "where id = ?")) {
//                statement.setObject(1,id);
////                try(ResultSet resultSet = statement.executeQuery("select * from user_login")) {
//                try(ResultSet resultSet = statement.executeQuery()) {
//                    while (resultSet.next()) {
//                        map.put("id",resultSet.getLong("id"));
//                        map.put("userCode",resultSet.getString(2));
//                        map.put("password",resultSet.getString(3));
//                    }
//                }
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return map;
        //spring template1
//        return jdbcTemplate.execute((Connection con)->{
//            Map<String,Object> map = new HashMap<>();
//            try(PreparedStatement statement = con
//                    .prepareStatement("select * from user_login where id = ?")) {
//                statement.setObject(1,id);
//                try(ResultSet resultSet = statement.executeQuery()) {
//                    while (resultSet.next()) {
//                        map.put("id",resultSet.getLong("id"));
//                        map.put("userCode",resultSet.getString(2));
//                        map.put("password",resultSet.getString(3));
//                    }
//                }
//            }
//            return map;
//        });
        //spring template2
//        return jdbcTemplate.queryForObject("select * from user_login where id = ?",
//            new Object[] {id},
//            (ResultSet resultSet,int rowNum)-> {
//                Map map = new HashMap();
//                map.put("id",resultSet.getLong("id"));
//                map.put("userCode",resultSet.getString(2));
//                map.put("password",resultSet.getString(3));
//                return map;
//            });
        //spring transaction
        Map<String,Object> map = null;
        TransactionStatus tx = null;
        try {
            tx = txManager.getTransaction(new DefaultTransactionAttribute());
            map = jdbcTemplate.execute((Connection con)->{
                Map<String,Object> map1 = new HashMap<>();
                try(PreparedStatement statement = con
                        .prepareStatement("select * from user_login where id = ?")) {
                    statement.setObject(1,id);
                    try(ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            map1.put("id",resultSet.getLong("id"));
                            map1.put("userCode",resultSet.getString(2));
                            map1.put("password",resultSet.getString(3));
                        }
                    }
                }
                return map1;
            });
            txManager.commit(tx);
        } catch (RuntimeException e) {
            txManager.rollback(tx);
        }
        return map;
        //mybatis
//        UserLogin userLogin = userLoginMapper.queryById(id);
//        Map<String,Object> map = new HashMap<>();
//        map.put("id",userLogin.getId());
//        map.put("userCode",userLogin.getUserCode());
//        map.put("password",userLogin.getUserPassword());
//        return map;
    }

    @Transactional
    public long insert(Map<String,Object> map) {
        long n = -1;
        try {
            Connection connection = DatabaseConfig.getConnection();
            try(PreparedStatement statement = connection.prepareStatement(
                    "insert into user_login (user_code,user_password) value (?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setObject(1,map.get("user_code"));
                statement.setObject(2,map.get("user_password"));
                statement.executeUpdate();
                try(ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) n = resultSet.getLong(1);
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return n;
    }

    public List<UserLogin> queryListByPlus() {
        return userLoginMapper.selectList(null);
    }

    public List<UserLogin> queryListBySession() {
        // 查询数据
        List<UserLogin> list;
        Map<String, Object> map = new HashMap<>();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            list = sqlSession.selectList("com.example.boost.mapper.UserLoginMapper.query", map);
            Future<Long> future = (Future<Long>) map.get("future");
            System.out.println("count is: " + future.get());
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
        return list;
    }
}
