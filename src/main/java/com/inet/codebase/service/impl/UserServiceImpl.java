package com.inet.codebase.service.impl;

import com.inet.codebase.entity.User;
import com.inet.codebase.mapper.ResourceMapper;
import com.inet.codebase.mapper.UserMapper;
import com.inet.codebase.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HCY
 * @since 2020-10-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private ResourceMapper resourceMapper;

    /**
     * 通过id序号查询到用户的权限
     * @author HCY
     * @since 2020-10-11
     * @param userId 用户的UUID序号
     * @return User对象实体类
     */
    @Override
    public User getUserAndRole(String userId) {
        User user = userMapper.getUserAndRole(userId);
        List<com.inet.codebase.entity.Resource> resources = resourceMapper.getListAndResource(userId);
        user.setResourceList(resources);
        return user;
    }
}
