package com.inet;

import com.inet.codebase.entity.User;
import com.inet.codebase.mapper.UserMapper;
import com.inet.codebase.service.UserService;
import com.inet.codebase.utlis.JwtUtils;
import com.inet.codebase.utlis.ShiroMd5Utlis;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.security.provider.MD5;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class HcyApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;


    @Test
    void contextLoads() {
        String userId = "F705347400044C8FBC6228C496BCAB23";
        User user = userService.getUserAndRole(userId);
        System.out.println(user);
    }

    @Test
    void contextLoads1() {
        Map<String , Object> map = new HashMap<>();
        map.put("account","HCY");
        map.put("password","123");
    }

    @Test
    void contextLoads2() {
        User user = new User();
        user.setUserName("HHH");
        user.setUserPassword("123");
        user.setUserRole("7095AE08AF5C4DBBBEC48B7821D01278");
        boolean save = userService.save(user);
        System.out.println(save);
    }

    @Test
    void contextLoads3() {
        Map<String , String> map = new HashMap<>();
        map.put("userName","HCY");
        map.put("userPassword","123");
        String token = JwtUtils.getToken(map);
        System.out.println(token);
    }


    @Test
    void contextLoads4() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyUGFzc3dvcmQiOiIxMjMiLCJ1c2VyTmFtZSI6IkhDWSIsImV4cCI6MTYwMzAxNjYwMH0.iZEbLtrqjULrP81-M3na9t2lZdwnQ2d9KNLtlQjpIts";
        System.out.println(JwtUtils.getString(token, "userName"));
    }


    @Test
    void contextLoads5() {
        String password = "123";
        //hoPFcz2CnhfU698u7ibq+Q==
        System.out.println(ShiroMd5Utlis.encryption(password));
    }
}
