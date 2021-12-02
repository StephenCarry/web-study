package com.yaoo.serivce;

import com.yaoo.aop.Logging;
import com.yaoo.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.ZoneId;
import java.util.List;

@Component
public class UserService {
    @Autowired
    private List<Dao> daos;

    @Autowired(required = false)
    @Qualifier("utc8")
    private ZoneId zoneId = ZoneId.systemDefault();

    @Logging("run")
    public void run() {
        for (Dao dao: daos) {
            dao.query();
        }
        System.out.println(zoneId);
    }

    @PostConstruct
    public void preHandle() {
        System.out.println("UserService was created");
    }

    @PreDestroy
    public void afterHandle() {
        System.out.println("UserService was died");
    }
}
