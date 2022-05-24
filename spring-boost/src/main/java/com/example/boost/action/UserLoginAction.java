package com.example.boost.action;

import com.example.boost.dao.UserLoginDao;
import com.example.boost.entry.UserLogin;
import com.example.boost.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserLoginAction {
    @Autowired
    UserLoginDao userLoginDao;
    @Autowired
    UserLoginService userLoginService;

    @RequestMapping("/queryById")
    @ResponseBody
    public Map getOne() {
        return userLoginDao.queryById(1);
    }

    @RequestMapping("/query")
    @ResponseBody
    public List getList() {
        return userLoginService.queryList();
    }

    @RequestMapping("/insert")
    @ResponseBody
    public Long insertUser() {
        Map<String,Object> map = new HashMap<>();
        map.put("user_code","dev");
        map.put("user_password","123456");
        map.put("id",userLoginDao.insert(map));
        return userLoginDao.insert(map);
    }

    @RequestMapping("/plus")
    @ResponseBody
    public List<UserLogin> getListPlus()  {
        return userLoginDao.queryListByPlus();
    }

}
