package com.inet.codebase.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.inet.codebase.entity.User;
import com.inet.codebase.service.UserService;
import com.inet.codebase.utlis.JwtUtils;
import com.inet.codebase.utlis.Result;
import com.inet.codebase.utlis.ShiroMd5Utlis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HCY
 * @since 2020-10-10
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录请求
     */
    @PostMapping("/login")
    public Result postLogin(@RequestParam("Account") String account,
                            @RequestParam("Password") String password){
        //进行条件的构造
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String , Object> condition = new HashMap<>();
        condition.put("user_name",account);
        condition.put("user_password", ShiroMd5Utlis.encryption(password));
        //插入条件
        queryWrapper.allEq(condition);
        User user = userService.getOne(queryWrapper);
        //判断user是否为null
        if (user == null){
            return new Result("登录失败,未找到用户","登录请求",101);
        }
        //不是null则创建token
        Map<String , String> tokenMap = new HashMap<>();
        tokenMap.put("userName", user.getUserName());
        tokenMap.put("userId", user.getUserId());
        //创建token
        String token = JwtUtils.getToken(tokenMap);
        //创建map
        Map<String , Object> result = new HashMap<>();
        result.put("token",token);
        result.put("message","登录成功");
        return new Result(result,"登录请求",100);
    }

    @GetMapping("/getAdmin")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    @RequiresPermissions("edit")
    public Result getAdmin(){
        return new Result("ADMIN" , "admin",100);
    }

}
