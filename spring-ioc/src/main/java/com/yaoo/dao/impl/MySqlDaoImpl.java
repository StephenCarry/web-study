package com.yaoo.dao.impl;

import com.yaoo.dao.Dao;
import org.springframework.stereotype.Component;

@Component
public class MySqlDaoImpl implements Dao {
    public void query() {
        System.out.println("MySQl实现");
    }
}
