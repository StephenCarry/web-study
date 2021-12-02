package com.yaoo.dao.impl;

import com.yaoo.dao.Dao;
import org.springframework.stereotype.Component;

@Component
public class Db2DaoImpl implements Dao {
    public void query() {
        System.out.println("Db2实现");
    }
}
