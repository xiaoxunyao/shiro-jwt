package com.inet.codebase.mapper;

import com.inet.codebase.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author HCY
 * @since 2020-10-10
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 通过id序号查询到用户的权限
     * @author HCY
     * @since 2020-10-11
     * @param userID 用户序号
     * @return User实体类
     */
    User getUserAndRole(String userID);
}
