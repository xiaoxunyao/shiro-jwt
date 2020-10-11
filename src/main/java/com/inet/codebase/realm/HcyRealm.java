package com.inet.codebase.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.inet.codebase.entity.User;
import com.inet.codebase.service.UserService;
import com.inet.codebase.utlis.JwtUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * HcyRealm
 *
 * @author HCY
 * @since 2020/10/11
 */
@Service
public class HcyRealm extends AuthorizingRealm {
    private static final Logger LOGGER  = LogManager.getLogger(HcyRealm.class);


    private UserService userService;

    @Resource
    public UserService setUserService(UserService userService) {
        return this.userService = userService;
    }

    /**
     * 一定需要创建,不然出错
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 权限认证
     * @author HCY
     * @since 2020-10-11
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取
        String userId = JwtUtils.getString(principalCollection.toString(), "userId");
        User user = userService.getUserAndRole(userId);
        //创建 simpleAuthorizationInfo 对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //添加 role 权限
        simpleAuthorizationInfo.addRole(user.getRoleName());
        //数据的转换
        Set<String> permission = new HashSet<>();
        //循环插入
        for (com.inet.codebase.entity.Resource resource : user.getResourceList()){
            permission.add(resource.getPermissionName());
        }
        //添加资源
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 身份验证
     * @author HCY
     * @since 2020-10-11
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        //通过解密获得账号和密码
        String userId = JwtUtils.getString(token, "userId");
        //进行查询
        User user = userService.getById(userId);
        if (user == null){
            throw new AuthenticationException("用户不存在");
        }
        if ( ! JwtUtils.verify(token)){
            throw new AuthenticationException("token出现错误");
        }
        return new SimpleAuthenticationInfo(token,token,"my_realm");

    }
}
