package com.example.boost.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.boost.entry.UserLogin;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLoginMapper extends BaseMapper<UserLogin> {
    @Select("select id, user_code as userCode, user_password as userPassword from user_login")
    List<UserLogin> query();

    @Select("select * from user_login where id = #{idd}")
    UserLogin queryById(@Param("idd") long id);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into user_login (user_code,user_password) values (#{userCode},#{userPassword})")
    void insert(@Param("userCode") String userCode, @Param("userPassword") String userPassword);
}
