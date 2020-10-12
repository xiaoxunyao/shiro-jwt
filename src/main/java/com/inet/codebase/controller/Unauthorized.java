package com.inet.codebase.controller;

import com.inet.codebase.utlis.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * unauthorized
 *
 * @author HCY
 * @since 2020/10/11
 */
@Api(value = "发送错误接口",tags = {"错误的收集类"})
@RestController
public class Unauthorized {

    @ApiOperation("产生错误集合")
    @RequestMapping(path = "/unauthorized/{message}")
    public Result unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return new Result(message , "错误请求",104);
    }

}
