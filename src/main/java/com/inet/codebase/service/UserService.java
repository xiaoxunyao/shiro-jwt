package com.inet.codebase.service;

import com.inet.codebase.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HCY
 * @since 2020-10-10
 */
public interface UserService extends IService<User> {
    /**
     * 通过id序号查询到用户的权限
     * @author HCY
     * @since 2020-10-11
     * @param userId 用户的UUID序号
     * @return User实体类
     */
    User getUserAndRole(String userId);


}
