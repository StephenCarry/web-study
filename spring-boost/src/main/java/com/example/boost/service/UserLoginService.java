package com.example.boost.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.boost.dao.UserLoginDao;
import com.example.boost.entry.UserLogin;
import com.example.boost.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UserLoginService {
    @Autowired
    final static Logger logger = LoggerFactory.getLogger(UserLoginService.class);
    @Autowired
    UserLoginDao userLoginDao;
    @Autowired
    RedisUtil redisUtil;

    public List queryList() {
        //查询redis缓存
        Jedis redis = redisUtil.getJedis();
        String redisKey = "user-login-list";
        String redisValue = redis.get(redisKey);
        List<UserLogin> list;
        if (redisValue==null || "".equals(redisValue)) {
            logger.info("redis not have key, add it!");
            list = userLoginDao.queryList();
            if (list != null && list.size() != 0) {
                redis.set(redisKey, String.valueOf(list));
            }
        } else {
            logger.info("get key for redis!");
            list = JSON.parseArray(redisValue, UserLogin.class);
        }
        redis.expire(redisKey,5);
        redis.close();
        return list;
    }
}
