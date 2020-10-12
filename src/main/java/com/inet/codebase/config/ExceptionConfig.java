package com.inet.codebase.config;

import com.inet.codebase.utlis.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "异常处理" , tags = {"异常处理的接口"})
@RestControllerAdvice
public class ExceptionConfig {

    /**
     * 捕获shiro的异常
     * @author HCY
     * @since 2020-10-11
     * @param exception 异常元素
     * @return Result
     */
    @ApiOperation("捕获shiro异常")
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
    @ApiOperation("捕获权限异常")
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
    @ApiOperation("捕获所有异常")
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
