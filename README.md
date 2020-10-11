# SpringBoot + JWT + Shiro

### 技术

**语言 : [JAVA](https://www.java.com/zh-CN/) + [MySQL](https://www.mysql.com/)**

**框架 : [SpringBoot](https://spring.io/) + [MyBatis](https://mybatis.org/mybatis-3/zh/index.html) + [MyBatis-Plus](https://baomidou.com/) + [Shiro](http://shiro.apache.org/) + [JWT](https://jwt.io/)**

**工具 : [Maven](https://mvnrepository.com) + [IDEA](https://www.jetbrains.com/) + [Navicat](http://www.navicat.com.cn/) + [PostMan](https://www.postman.com/)**

### 数据库文件

- 文件的所在地
  - [SQL文件](https://github.com/xiaoxunyao/shiro-jwt/tree/master/src/main/resources/static)

### 代码

#### 创建一个JWT的工具类,名为JwtUtlis

```java
package com.inet.codebase.utlis;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * JWT得工具类
 * @author Hcy
 * @since 2020-10-09
 */
public class JwtUtils {

    private static final String SING = "HCY";

    /**
     * 产生token
     * @author HCY
     * @since 2020-10-09
     */
    public static String getToken(Map<String , String> map){
        //设置过期时间 , 过期时间为7天
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE , 7);

        //创建 Builder
        JWTCreator.Builder builder = JWT.create();
        //遍历map,设置token参数
        map.forEach((key,value)->{
            builder.withClaim(key,value);
        });
        //设置过期时间
        String token = builder.withExpiresAt(instance.getTime())
                //设置签名
                .sign(Algorithm.HMAC256(SING));

        return token;
    }

    /**
     * 验证token是否合法
     * @author HCY
     * @since 2020-10-09
     */
    public static Boolean verify(String token){
        try {
            JWT.require(Algorithm.HMAC256(SING))
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取token中的某一个数据(String)
     * @author HCY
     * @since 2020-10-11
     * @param token
     * @param search
     * @return
     */
    public static String getString(String token,String search){
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            String data = decodedJWT.getClaim(search).asString();
            return data;
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取token中的某一个数据(Integer)
     * @author HCY
     * @since 2020-10-11
     * @param token
     * @param search
     * @return
     */
    public static Integer getInteger(String token,String search){
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            Integer data = decodedJWT.getClaim(search).asInt();
            return data;
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取token中的某一个数据(Double)
     * @author HCY
     * @since 2020-10-11
     * @param token
     * @param search
     * @return
     */
    public static Double getDouble(String token,String search){
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            Double data = decodedJWT.getClaim(search).asDouble();
            return data;
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取token得信息
     * @author HCY
     * @since 2020-10-09
     */
    public static DecodedJWT getTokenInfo(String token){
        //获取 token 得 DecodedJWT
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING))
                .build()
                .verify(token);

        return verify;
    }
}

```

#### 创建一个加密的字符串

```java
package com.inet.codebase.utlis;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * ShiroMd5Utlis
 *
 * @author HCY
 * @since 2020/10/11
 */
public class ShiroMd5Utlis {

    /**
     * 加密盐
     */
    private static final String SIGN = "HCY";

    /**
     * 加密算法
     */
    private static final String ALG = "MD5";

    /**
     * 散列次数
     */
    private static final int HASH = 1024;

    /**
     * 创建加密过后的字符串
     * @param password
     * @return
     */
    public static String encryption(String password){
        return new SimpleHash(ALG, password, ByteSource.Util.bytes(SIGN), HASH).toString();
    }

}

```

#### 创建一个JWT的Token类

```java
package com.inet.codebase.realm;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JwtToken
 *
 * @author HCY
 * @since 2020/10/11
 */
public class JwtToken implements AuthenticationToken {
    /**
     * 密钥
     */
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

```

#### 创建一个Realm,用了自己的名字进行前景命名

```java
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

```

#### 创建一个JWT的过滤器

1. 检验请求头是否带有 token `((HttpServletRequest) request).getHeader("Token") != null`
2. 如果带有 token，执行 shiro 的 login() 方法，将 token 提交到 Realm 中进行检验；如果没有 token，说明当前状态为游客状态（或者其他一些不需要进行认证的接口）

```java
package com.inet.codebase.filter;

import com.inet.codebase.realm.JwtToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * JwtFilter
 *
 * @author HCY
 * @since 2020/10/11
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * 果带有 token，则对 token 进行检查，否则直接通过
     * @author HCY
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws UnauthorizedException
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
        //判断请求的请求头是否带上 "Token"
        if (((HttpServletRequest) request).getHeader("Token") != null) {
            //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
            try {
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                //token 错误
                return false;
            }
        }
        //如果请求头不存在 Token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }

    /**
     * 执行登录的操作
     * @author HCY
     * @since 2020-10-11
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Token");
        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 判断用户是否想要登入。
     * 检测 header 里面是否包含 Token 字段
     * @author HCY
     * @since 2020-10-11
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("Token");
        return token != null;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /unauthorized/**
     */
    private void responseError(ServletResponse response, String message) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            //设置编码，否则中文字符在重定向时会变为空字符串
            message = URLEncoder.encode(message, "UTF-8");
            httpServletResponse.sendRedirect("/unauthorized/" + message);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}

```

#### 创建一个Shiro的配置文件

```java
package com.inet.codebase.config;

import com.inet.codebase.filter.JwtFilter;
import com.inet.codebase.realm.HcyRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * ShiroConfig
 *
 * @author HCY
 * @since 2020/10/11
 */
@Configuration
public class ShiroConfig {
    /**
     * 先走 filter ，然后 filter 如果检测到请求头存在 token，则用 token 去 login，走 Realm 去验证
     */
    @Bean
    public ShiroFilterFactoryBean factory(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        //设置我们自定义的JWT过滤器
        filterMap.put("jwt", new JwtFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        // 设置无权限时跳转的 url;
        factoryBean.setUnauthorizedUrl("/unauthorized/无权限");
        Map<String, String> filterRuleMap = new HashMap<>();
        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/**", "jwt");
        // 访问 /unauthorized/** 不通过JWTFilter
        filterRuleMap.put("/unauthorized/**", "anon");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager(HcyRealm hcyRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义 realm.
        securityManager.setRealm(hcyRealm);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 添加注解支持
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}


```

#### 创建一个异常捕获类

- **当我们写的接口拥有以上的注解时，如果请求没有带有 token 或者带了 token 但权限认证不通过，则会报 UnauthenticatedException 异常，但是我在 ExceptionController 类对这些异常进行了集中处理**

  

```java
package com.inet.codebase.config;

import com.inet.codebase.utlis.Result;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * ExceptionConfig
 * 捕获异常
 * @author HCY
 * @since 2020/10/11
 */
@RestControllerAdvice
public class ExceptionConfig {

    /**
     * 捕获shiro的异常
     * @author HCY
     * @since 2020-10-11
     * @param exception 异常元素
     * @return Result
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Result handle401(ShiroException exception){
        return new Result(exception.getMessage(),"权限异常",104);
    }

    /**
     * 捕获UnauthorizedException异常
     * @author HCY
     * @since 2020-10-11
     * @return Result
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public Result handel401(){
        return new Result("无权限","权限异常",104);
    }

    /**
     * 捕捉其他所有异常
     * @author HCY
     * @since 2020-10-11
     * @return Result
     */
    @ExceptionHandler(Exception.class)
    public Result globalException(HttpServletRequest request, Throwable ex) {
        System.out.println(request);
        return new Result(getStatus(request).value(), ex.getMessage(), 104);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}

```

#### 权限的控制

```java
//拥有 admin 角色可以访问
@RequiresRoles("admin")
// 拥有 user 或 admin 角色可以访问
@RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
// 拥有 vip 和 normal 权限可以访问
@RequiresPermissions(logical = Logical.AND, value = {"edit", "view"})

// 拥有 user 或 admin 角色，且拥有 vip 权限可以访问
@GetMapping("/getVipMessage")
@RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
@RequiresPermissions("vip")
public ResultMap getVipMessage() {
    return resultMap.success().code(200).message("成功获得 vip 信息！");
}
```

